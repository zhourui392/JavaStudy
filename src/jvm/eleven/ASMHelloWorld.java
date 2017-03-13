package jvm.eleven;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 用asm生成字节码
 *
 */
public class ASMHelloWorld extends ClassLoader {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, "jvm/eleven", null, "java/lang/Object", null);
        // 方法开始init
        MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mw.visitVarInsn(Opcodes.ALOAD, 0); // this 入栈
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(0, 0);
        mw.visitEnd(); // 方法init结束
        // main方法开始
        mw = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        /**
         * 使用ASM，通过字节码 完成以下代码： int a=6; int b=7; int c=(a+b)*3;
         * System.out.println(c);
         */
        // 把变量放入局部变量表里
        mw.visitIntInsn(Opcodes.BIPUSH, 6);
        mw.visitVarInsn(Opcodes.ISTORE, 3);
        mw.visitIntInsn(Opcodes.BIPUSH, 7);
        mw.visitVarInsn(Opcodes.ISTORE, 4);
        // 操作数栈
        mw.visitVarInsn(Opcodes.ILOAD, 3);
        mw.visitVarInsn(Opcodes.ILOAD, 4);
        mw.visitInsn(Opcodes.IADD);
        mw.visitIntInsn(Opcodes.BIPUSH, 3);
        mw.visitInsn(Opcodes.IMUL);
        mw.visitVarInsn(Opcodes.ISTORE, 2);
        // 打印出来
        mw.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitVarInsn(Opcodes.ILOAD, 2);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(0, 0);

        mw.visitEnd(); // main方法结束

        final byte[] code = cw.toByteArray();

        ASMHelloWorld loader = new ASMHelloWorld();
        Class<?> exampleClass = loader.defineClass("jvm.eleven", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });

    }

}
