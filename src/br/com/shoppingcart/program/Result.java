package br.com.shoppingcart.program;

import java.util.ArrayList;

public class Result {

	public static void main(String[] args) {
		
		String test = "dabbcabcd";
		
		char[] stringCharArr = test.toCharArray();
		
		ArrayList<Character> allLettersArrayList = new ArrayList<Character>();
		
		//determines all letters of the string 
		for(int i = 0; i < stringCharArr.length; i++) {
			if(!allLettersArrayList.contains(stringCharArr[i])){
				allLettersArrayList.add(stringCharArr[i]);
			}
		}
		
		for (int i = 0; i < allLettersArrayList.size(); i++) {
			System.out.println(allLettersArrayList.get(i).toString());
		}
		
		//string com as letras encontradas
		String palavraEncontrada = "";
		for (int i = 0; i < allLettersArrayList.size(); i++) {
			palavraEncontrada += allLettersArrayList.get(i);
		}
		
		//determina as substrings que contem todas as letras
		
		ArrayList<String> substrings = new ArrayList<String>();
		
		
		for (int i = 0; i < stringCharArr.length - allLettersArrayList.size() + 1; i++) {
			
			String substringAtual = "";
			
			//adiciona os caracteres à verificação de substring atual
			for (int j = 0; j < stringCharArr.length; j++) {

				//verifica se a string atual já possui todos os caracteres da cadeia
				//se não tiver, adiciona o caractere atual
				//se tiver completo, sai do for
				
				if(substringAtual.length() == 0) {
					substringAtual = substringAtual + test.charAt(j);
					continue;
				}
				
				if(isAllCharsOnString(palavraEncontrada, substringAtual)) {
					break;
				} else {
					substringAtual = substringAtual + test.charAt(j);
				}
					
			}
			
			substrings.add(substringAtual);
				
		}
		
		for (int i = 0; i < substrings.size(); i++) {
			System.out.println(substrings.get(i).toString());
		}
	
	}
	
	public static boolean isAllCharsOnString(String str, String str2) {
		
		ArrayList<Character> strChars = new ArrayList<Character>();
		ArrayList<Character> str2Chars = new ArrayList<Character>();
		
		for (int i = 0; i < str.length(); i++) {
			strChars.add(str.charAt(i));
		}
		
		for (int i = 0; i < str2.length(); i++) {
			str2Chars.add(str2.charAt(i));
		}
		
		for (int i = 0; i < str2Chars.size(); i++) {
			if(str2Chars.containsAll(strChars)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int shortestSubstring(String s) {
		
		
		
		return 0;
	}

}
