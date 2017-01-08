package concurrent.four;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * source from aminocbbs LockFreeVector
 * @param <E>
 */
public class LockFreeStack<E> extends Stack<E>{

    private static final int MARK_FIRST_BIT = 0x80000000;

    private static final int FIRST_BUCKET_SIZE = 8;

    private static final int N_BUCKET = 30;

    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;

    static class WriteDescriptor<E> {

        public E oldV;

        public E newV;

        public AtomicReferenceArray<E> addr;

        public int addrInd;

        public WriteDescriptor(AtomicReferenceArray<E> addr, int addrInd,
                               E oldV, E newV) {
            this.addr = addr;
            this.addrInd = addrInd;
            this.oldV = oldV;
            this.newV = newV;
        }

        public void doIt() {
            addr.compareAndSet(addrInd, oldV, newV);
        }
    }

    static class Descriptor<E> {

        public int size;

        volatile WriteDescriptor<E> writeop;

        public Descriptor(int size, WriteDescriptor<E> writeop) {
            this.size = size;
            this.writeop = writeop;
        }

        public void completeWrite() {
            WriteDescriptor<E> tmpOp = writeop;
            if (tmpOp != null) {
                tmpOp.doIt();
                writeop = null;
            }
        }
    }

    private AtomicReference<Descriptor<E>> descriptor;

    private static final int ZERO_NUM_FIRST = Integer
            .numberOfLeadingZeros(FIRST_BUCKET_SIZE);

    public LockFreeStack() {
        buckets = new AtomicReferenceArray<>(N_BUCKET);
        buckets.set(0, new AtomicReferenceArray<>(FIRST_BUCKET_SIZE));
        descriptor = new AtomicReference<>(new Descriptor<>(0, null));
    }

    @Override
    public E push(E e) {
        Descriptor<E> desc;
        Descriptor<E> newd;
        do {
            desc = descriptor.get();
            desc.completeWrite();

            int pos = desc.size + FIRST_BUCKET_SIZE;
            int zeroNumPos = Integer.numberOfLeadingZeros(pos);
            int bucketInd = ZERO_NUM_FIRST - zeroNumPos;

            if (buckets.get(bucketInd) == null) {
                int newLen = 2 * buckets.get(bucketInd - 1).length();

                buckets.compareAndSet(bucketInd, null,
                        new AtomicReferenceArray<>(newLen));
            }

            int idx = (MARK_FIRST_BIT >>> zeroNumPos) ^ pos;

            newd = new Descriptor<>(desc.size + 1, new WriteDescriptor<>(
                    buckets.get(bucketInd), idx, null, e));
        } while (!descriptor.compareAndSet(desc, newd));
        descriptor.get().completeWrite();
        return e;
    }

    @Override
    public E pop() {
        Descriptor<E> desc;
        Descriptor<E> newd;
        E elem;
        do {
            desc = descriptor.get();
            desc.completeWrite();
            if (desc.size==0){
                return null;
            }
            int pos = desc.size + FIRST_BUCKET_SIZE - 1;
            int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                    - Integer.numberOfLeadingZeros(pos);
            int idx = Integer.highestOneBit(pos) ^ pos;
            elem = buckets.get(bucketInd).get(idx);
            newd = new Descriptor<>(desc.size - 1, null);
        } while (!descriptor.compareAndSet(desc, newd));
        return elem;
    }

    @Override
    public int size() {
        return descriptor.get().size;
    }

    @Override
    public E peek() {
        Descriptor<E> desc = descriptor.get();
        int pos = desc.size + FIRST_BUCKET_SIZE - 1;
        int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                    - Integer.numberOfLeadingZeros(pos);
        int idx = Integer.highestOneBit(pos) ^ pos;
        return buckets.get(bucketInd).get(idx);
    }
}
