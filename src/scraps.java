import java.math.BigInteger;

public class scraps {

	public static void main(String[] args) {
		byte[] t = {(byte) 0xAC};
		byte[] q = {(byte) 0xEA, (byte)0x22, (byte)0x54, (byte) 0xEA, (byte)0x22, 
					(byte)0x54, (byte) 0xEA, (byte)0x22, (byte)0x54, (byte) 0xEA, 
					(byte)0x22, (byte)0x54, (byte) 0xEA, (byte)0x22, (byte)0x54, 
					(byte) 0xEA, (byte)0x22, (byte)0x54, (byte) 0xEA, (byte)0x22, 
					(byte)0x54, (byte) 0xEA, (byte)0x22, (byte)0x54, (byte) 0xEA, 
					(byte)0x22, (byte)0x54, (byte) 0xEA, (byte)0x22, (byte)0x54, 
					(byte) 0xEA, (byte)0x22, (byte)0x54, (byte) 0xEA, (byte)0x22, 
					(byte)0x54, (byte) 0xEA, (byte)0x22, (byte)0x54};
		byte[] l = encode(q, t);
		byte[] m = decode(l);
		System.out.printf("0x%02X", m[0]);
		System.out.println();
		/*
		byte[] barry = {0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75,0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75, 0x12, 0x75};
		byte[] batty = {0x15, 0x51};
		byte[] dumb = encode(barry, batty);
		byte[] dumber = decode(dumb);
		System.out.println(dumb.length);
		System.out.println(dumber.length);
		for (byte a : dumber) {
			System.out.printf("0x%02X", a);
			System.out.println();
		}
		/*byte[] barry = {0x34,(byte) 0xFA, (byte)0x2C};
		byte[] batty = allBytesToBits(barry);
		// System.out.printf("0x%02X", C);
		for (byte a : batty) {
			System.out.printf("0x%02X", a);
			System.out.println();
		}
		System.out.println("-----------------------------------------------");
		byte[] bally = allBitsToBytes(batty);
		for (byte b : bally) {
			System.out.printf("0x%02X", b);
			System.out.println();
		}
		*/
	}
	
	public static byte[] encode(byte[] A, byte[] B){
		byte[] bBits = allBytesToBits(B);
		return encodeToByteArray(A, bBits);
	}
	
	public static byte[] decode(byte[] A){
		byte[] encodedBits = decodeToByteArray(A);
		return encodedBits;
	}

	
	public static byte[] allBytesToBits(byte[] A) {
		byte[] bigArray = new byte[A.length * 8];
		for (int i = 0; i < A.length; i++) {
			byte[] aTemp = byteToBits(A[i]);
			for (int z = 0; z < aTemp.length; z++) {
				bigArray[(i*8)+z] = aTemp[z];
			}
		}
		return bigArray;
	}

	public static byte[] byteToBits(byte A) {
		byte[] bits = new byte[8];
		for (int i = 7; i >= 0; i--) {
			bits[i] = (byte) ((A >> i) & 1);
		}
		return bits;
	}
	
	public static byte[] allBitsToBytes(byte[] A){
		byte[] bigArray = new byte[A.length/8];
		int bigIndex = 0;
		byte[] temp = new byte[8];
		for(int i = 0; i < A.length; i++){
			int modIndex = i % 8;
			temp[modIndex] = A[i];
			if(modIndex == 7){
				bigArray[bigIndex] = bitsToByte(temp);
				bigIndex++;
			}
		}
		return bigArray;
	}
	public static byte bitsToByte(byte[] A){
		byte one = 0x00;
		for (int i = 7; i >= 0; i--) {
			if (A[i] == 0x01) {
				one = (byte) (one | (1 << i));
			} else {
				one = (byte) (one & ~(1 << i));
			}
		}
		return one;
		
	}
	

	public static byte appendBitToByte(byte A, byte B) {
		byte mask = 0x01;
		byte val = (byte) (mask & B);
		byte ret;
		if (val == 0x01) {
			ret = (byte) (A | (1 << 0));
		} else {
			ret = (byte) (A & ~(1 << 0));
		}
		return ret;
	}

	public static byte retractBitFromByte(byte A) {
		byte ret = (byte) ((A >> 0) & 1);
		return ret;
	}
	
	public static byte[] encodeToByteArray(byte[] A, byte[] B){	
		byte[] finalBytes = A;
		BigInteger size  = BigInteger.valueOf(B.length); 	
		byte[] numBytes = size.toByteArray();
		int numLen = numBytes.length;
		byte[] numAsBits = new byte[24];
		byte[] tempBytes = allBytesToBits(numBytes);
		if (numLen > 3){
			//error message
			numLen = 3;	
		}
		int bitCounter = 0;
		for(int i = tempBytes.length-((numLen*8)); i < tempBytes.length; i++){
			numAsBits[bitCounter] = tempBytes[i];
			bitCounter++;
		}
		int numBitSize = numAsBits.length;
		for(int i = 0; i < numBitSize; i++){
			if(i < A.length){
				finalBytes[i] = appendBitToByte(A[i], numAsBits[i]);
			}else{
			//throw an error
			}
		}
		for(int i = 0; i < B.length; i++){
			if((numBitSize + i) < A.length){
				finalBytes[numBitSize+ i] = appendBitToByte(A[numBitSize + i], B[i]);
			}else{
				//throw an error
			}
		}
		
	return finalBytes;
	}
	
	public static byte[] decodeToByteArray(byte[] A){	
		byte[] cryptSize = new byte[24];
		for (int i =0; i < cryptSize.length; i++){
			if(i < A.length){
				cryptSize[i] = retractBitFromByte(A[i]);
			}
		}
		byte[] cryptSizeBytes = allBitsToBytes(cryptSize);
		byte[] crytpReverse = {cryptSizeBytes[2], cryptSizeBytes[1], cryptSizeBytes[0]};
		int size = new BigInteger(crytpReverse ).intValue();

		byte[] tempBytes = new byte[size];
		for(int i = 24; i < 24 + size; i++){
			tempBytes[i-24] = retractBitFromByte(A[i]);
		}

		byte[] finalBytes = allBitsToBytes(tempBytes);
		return finalBytes;
	}
}
