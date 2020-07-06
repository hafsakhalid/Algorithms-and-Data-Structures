public class Message {

		public String message;
		public int lengthOfMessage;

		

		public Message (String m){
			message = m;
			lengthOfMessage = m.length();
			this.makeValid();
		}
		
		public Message (String m, boolean b){
			message = m;
			lengthOfMessage = m.length();
		}
		
		/**
		 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
		 */
		public void makeValid(){
			//INSERT YOUR CODE HERE
		   
			message = message.toLowerCase();
				char[] allChars = message.toCharArray();
				String newMessage = "";
			for(int i = 0; i<allChars.length; i++) {
				if(allChars[i]>90 && allChars[i]<123) {
					newMessage += allChars[i];
				}
			}
			
			message = newMessage;
			//change length of message
			lengthOfMessage = newMessage.length();
			
		}
		
		/**
		 * prints the string message
		 */
		public void print(){
			System.out.println(message);
		}
		
		/**
		 * tests if two Messages are equal
		 */
		public boolean equals(Message m){
			if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
				return true;
			}
			return false;
		}
		
		/**
		 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
		 * @param key
		 */
		public void caesarCipher(int key){
			// INSERT YOUR CODE HERE
		  //key should be positive and less than 26
			key = key%26;
			String shifted = "";
				if (key<0) key +=26;
					
				char [] c = new char [lengthOfMessage];
			for (int i =0; i<lengthOfMessage; i++) {
				
				char temp = (message.charAt(i));
				//check if it changes it by the required amount 
				if (temp + key > 'z') temp-=26;  
				if (temp + key < 'a') temp+=26;
				c[i] = (char) (temp + key); 
				   
				   }
			 
			for (int i = 0; i<lengthOfMessage; i++) {
				shifted = shifted+ c[i];
				
			}
			 message = shifted.toString();
			
		}
			
			public void caesarDecipher(int key){
			this.caesarCipher(- key);
		}
		
		/**
		 * caesarAnalysis breaks the Caesar cipher
		 * you will implement the following algorithm :
		 * - compute how often each letter appear in the message
		 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
		 * - decipher the message using the key you have just computed
		 */
		public void caesarAnalysis(){
			// INSERT YOUR CODE HERE
		 //check to see how often each letter appears in a message 
			int [] charCount = new int [123];
				for (int i=0; i<lengthOfMessage; i++) { 
					int x = (int)message.charAt(i);
					charCount[x] ++;
             }
		
		//Now to find the most frequent letter 
				int y = 97;
				for (int i= 97; i<122; i++) {
					if(charCount[i]>charCount[y]) y=i;
				
				}
		
		int e = y-101;
				caesarDecipher(e);
		
		}
		
		/**
		 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
		 * @param key
		 */
		public void vigenereCipher (int[] key){
			// INSERT YOUR CODE HERE
	  //make two loops  
			StringBuffer buffer = new StringBuffer();
		//one of the loops is used to go through the message and the other one goes through the key 	
	for (int i =0; i<lengthOfMessage;)	{
	 int keepCount = 0;
	 	while (keepCount<key.length && i<lengthOfMessage) {
	 		char b = message.charAt(i);
	 		if (b+ key[keepCount]>122) b-=26;
	 		else if (b+ key[keepCount]<97) b+=26;
	 		b = (char)(b + key[keepCount]); 
	 		buffer.append(b);
	 		keepCount ++;
	 		i++;
	 		}
	
}
         message=buffer.toString ();
 
}

		/**
		 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
		 * @param key
		 */
		public void vigenereDecipher (int[] key){
			// INSERT YOUR CODE HERE
		for (int i=0; i<key.length; i++) key[i] = -key[i];
		vigenereCipher (key);
		
			}
		
		/**
		 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
		 * @param key
		 */
		public void transpositionCipher (int key){
			// INSERT YOUR CODE HERE
		 //checking to see if we need '***'
			int row = lengthOfMessage/key;
				if (lengthOfMessage%key>0) row = lengthOfMessage/key+1;
				char [][] buff = new char [row][key];
				int y = 0;
				for (int i=0; i<row; i++) {
					for(int j=0; j<key; j++) {
						if(y<lengthOfMessage) {
						buff[i][j] = message.charAt(y);
						y++;
					}
					else buff[i][j] = '*';
					
					}
				}
		
		//encrypting the message 
				StringBuffer buffer = new StringBuffer ();
				for (int i = 0; i<key; i++) {
					for (int j =0; j<row; j++) {
						buffer.append(buff[j][i]);
					}
				
				
				}
				message = buffer.toString();
				lengthOfMessage = message.length();
		
			}
		
		/**
		 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
		 * @param key
		 */
		public void transpositionDecipher (int key){
			// INSERT YOUR CODE HERE
		int row = lengthOfMessage/key; 
		char [][] buff = new char [row][key];
		StringBuffer buffer = new StringBuffer (); 
		int y = 0;
			for (int i = 0; i<key; i++) {
				for (int j =0; j<row; j++) {
					if(y<lengthOfMessage)buff[j][i] = message.charAt(y);
					y++;
				
				}
			}
			 //going back to the original message now 
			for (int i =0; i<row; i++) {
				for(int j=0; j<key; j++ ) {
					if (buff[i][j] !='*')buffer.append(buff[i][j]);
					else break;
				
				}
			}
		
		
		message = buffer.toString ();
		lengthOfMessage = message.length();
		
		
		
		
		
		
		
		
		
		
		
		}
		
	}




