package com.darian.chaper2;

public class TryFinally {
	public static void main(String[] args) {
		System.out.println(f1());
	}
	/**
	 public static java.lang.String f1();
    descriptor: ()Ljava/lang/String;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=3, args_size=0
         0: ldc           #34                 // String hello
         2: astore_0
         3: aload_0
         4: astore_2
         5: ldc           #36                 // String gupao
         7: astore_0
         8: aload_2
         9: areturn
        10: astore_1
        11: ldc           #36                 // String gupao
        13: astore_0
        14: aload_1
        15: athrow
	 * */
	public static String f1() {
		String str = "hello";
        try{
        	// int returnAddr = alloc; 1
			// str ref = returnAddr;  2
        	//return str == 1 + 2;    3
            return str;
        }
        finally{
            str = "gupao";   //4
        }
	}
}