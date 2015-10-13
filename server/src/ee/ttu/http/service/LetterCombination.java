package ee.ttu.http.service;

import java.util.List;

public class LetterCombination {
	int bigCount;
	String word = "";
	
	public String generateCombinations(int arraySize, List<String> possibleValues, int find){
		
		if (arraySize > 1){
			generateCombinations(arraySize-1, possibleValues, find);
		}
		
		int count = 0;
	    int carry;
	    int[] indices = new int[arraySize];
	    do {
	        count++;
	        bigCount++;
	        
	        /*
	         * This prints out all the combinations
	        for(int index : indices)
	            System.out.print(possibleValues.get(index) + " ");
	        System.out.println("");
	        */
	        
	        if (bigCount == find){
	        	//System.out.print("At " + find + " is ");
	        	for(int index : indices){
		        	//System.out.print(possibleValues.get(index));
		        	word +=possibleValues.get(index);
		        	
		        }
	        	return word;
	        	  
		        //System.out.println("");
	        }
	      

	        carry = 1;
	        for(int i = indices.length - 1; i >= 0; i--)
	        {
	            if(carry == 0)
	                break;

	            indices[i] += carry;
	            carry = 0;

	            if(indices[i] == possibleValues.size())
	            {
	                carry = 1;
	                indices[i] = 0;
	            }
	        }
	        
	    }
	    
	  
	    while(carry != 1); // Call this method iteratively until a carry is left over
	    //System.out.println("");
	    //System.out.println("Total values for size " + arraySize + " is " + count);
	    //bigCount += count;
	    //System.out.println("Total count is " + bigCount);
	    //System.out.println(" ");   
		return word;
	}
	
public String findWord(int arraySize, List<String> possibleValues, String findWord){
		
		if (arraySize > 1){
			findWord(arraySize-1, possibleValues, findWord);
		}
		
		int count = 0;
		String word = "";;
	    int carry;
	    int[] indices = new int[arraySize];
	    do {
	        count++;
	        bigCount++;
	        
	     
	        for(int index : indices){
	            //System.out.print(possibleValues.get(index) + " ");
	            word += possibleValues.get(index);
	        }
	        if (word.equals(findWord) ){
	        	System.out.println(word + " leitud @ " + bigCount);
        		return word;
        	}
	        
	       //System.out.println("word on: " + word);
	        word = "";
	        
	       // System.out.println(possibleValues.get(index));
	        //System.out.println(test);
	        	
  
		        //System.out.println("");
	        carry = 1;
	        for(int i = indices.length - 1; i >= 0; i--)
	        {
	            if(carry == 0)
	                break;

	            indices[i] += carry;
	            carry = 0;

	            if(indices[i] == possibleValues.size())
	            {
	                carry = 1;
	                indices[i] = 0;
	            }
	        }
	        
	    }
	    
	  
	    while(carry != 1); // Call this method iteratively until a carry is left over
	    //System.out.println("");
	    //System.out.println("Total values for size " + arraySize + " is " + count);
	    //bigCount += count;
	    //System.out.println("Total count is " + bigCount);
	    //System.out.println(" ");   
		return word;
	}
}

