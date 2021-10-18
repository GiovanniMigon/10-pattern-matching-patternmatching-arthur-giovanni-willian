# Problemas de Pattern Matching

## O problema


Dadas duas strings, s1 e s2, verificar a posição da primeira ocorrência de se s2 em s1, se existir.

Assim, se s1 = "ABCDCBDCBDACBDABDCBADF" e s1 = "ADF" o retorno seria 19.



## Enunciado 1

1. Faça um algortimo que resolva o problema acima.
   1. teste-o para strings de diversos tamanhos, até strings grandes (>500.000 caracteres). Conte o número de iterações e de instruções.
   1. qual a complexidade, no pior caso?

## Enunciado 2

2. O algoritmo de Rabin-Karp utiliza uma função hash para resolver o problema de busca de padrões em string. O algoritmo está dado abaixo.
 
```javascript
private int search(String pat, String txt) {
   int M = pat.length();
   int N = txt.length();
   long patHash = hash(pat, M);

   for (int i = 0; i <= N - M; i++) {
      long txtHash = hash(txt.subtring(i, i+M), M);
      if (patHash == txtHash)
         return i; // ocorrência? colisão?
   }
   return N; // nenhuma ocorrência
}
```

O hash pode ser calculado utilizando o algoritmo de Horner.
Algoritmo de Horner para calcular o hash de uma string s[0..M-1]:

```javascript
//
//Notação: o padrão tem M caracteres, o texto tem N caracteres, o alfabeto tem R caracteres  (0 … R−1) 
//              Q é o módulo para o cálculo do Hash.
//              Qual o valor de Q?  Escolha Q igual a um primo grande para minimizar a chance de colisões.
//                       Por exemplo: o maior primo que possa ser representado com um int

private long hash(String s, int M) {
   long h = 0;
   for (int j = 0; j < M; j++)
      h = (h * R + s.charAt(j)) % Q;
   return h;
}
```

3. Implemente o algoritmo acima, para resolver o mesmo problema anterior.

   3.1. teste-o para strings grandes (>500.000 caracteres). Conte o número de iterações e de instruções.
   
   3.2. qual a complexidade, no pior caso?

## Enunciado 3
4. O algoritmo de Knuth-Morris-Pratt utiliza um vetor auxiliar (LPS - Longest Proper Prefix) para resolver o problema de busca de padrões em string. O algoritmo está dado abaixo.

```javascript
// KMP pattern searching algorithm 

class KMP_String_Matching { 
	void KMPSearch(String pat, String txt) 
	{ 
		int M = pat.length(); 
		int N = txt.length(); 

		// cria lps[] que vai guardar o maior 
		// valor prefixo sufixo para o padrão 
		int lps[] = new int[M]; 
		int j = 0; // index for pat[] 

		// Calcula lps[] 
		computeLPSArray(pat, M, lps); 

		int i = 0; // index for txt[] 
		while (i < N) { 
			if (pat.charAt(j) == txt.charAt(i)) { 
				j++; 
				i++; 
			} 
			if (j == M) { 
				System.out.println("Found pattern "
								+ "at index " + (i - j)); 
				j = lps[j - 1]; 
			} 

			// mismatch após j matches 
			else if (i < N && pat.charAt(j) != txt.charAt(i)) { 
				// Não faz match dos caracteres lps[0..lps[j-1]], 
				// não é necesssário, eles combinarão 
				if (j != 0) 
					j = lps[j - 1]; 
				else
					i = i + 1; 
			} 
		} 
	} 

	void computeLPSArray(String pat, int M, int lps[]) 
	{ 
		// tamanho do maior prefixo sufixo anterior 
		int len = 0; 
		int i = 1; 
		lps[0] = 0; // lps[0] is always 0 

		// loop calcula lps[i] for i = 1 to M-1 
		while (i < M) { 
			if (pat.charAt(i) == pat.charAt(len)) { 
				len++; 
				lps[i] = len; 
				i++; 
			} 
			else // (pat[i] != pat[len]) 
			{ 
				if (len != 0) { 
					len = lps[len - 1]; 
				} 
				else // if (len == 0) 
				{ 
					lps[i] = len; 
					i++; 
				} 
			} 
		} 
	} 
```

5. Implemente o algoritmo acima, para resolver o mesmo problema anterior.

   5.1. Teste-o para strings grandes (>500.000 caracteres); 
   
   5.2. Conte o número de iterações e de instruções.
   
Qual a complexidade, no pior caso?
