package combinacoes;

import java.util.ArrayList;
import java.util.List;

public class Votacao {

	private List<int[][]> segmentacoes;
	
	private double[][] imagem;

	public Votacao(List<double[][]> listaSegmentacao) throws Exception {
		this.segmentacoes = new ArrayList<int[][]>();
		
		//Segmentações 
		for (double[][] segmentacao : listaSegmentacao) {
			this.segmentacoes.add(matrizDoubleToInt(segmentacao));
		}
		
		int linhas = listaSegmentacao.get(0).length;
		int colunas = listaSegmentacao.get(0)[0].length;
		
		this.imagem = new double[linhas][colunas];
			  
	}
	
	public double[][] algoritmo() throws Exception {
		
		
		for (int i = 0; i < imagem.length; i++) {
			for (int j = 0; j < imagem[i].length; j++) {
				int classe0 = 0;
				int classe1 = 0;
				for (int s = 0; s < segmentacoes.size(); s++) {
					if (segmentacoes.get(s)[i][j] == 0) {
						classe0++;
					} else if (segmentacoes.get(s)[i][j] == 1) {
						classe1++;
					} else {
						System.err.println(imagem[i][j]);
						throw new Exception();
					}
				}
				
				if (classe0 >= classe1) {
					this.imagem[i][j] = 0;
				} else {
					this.imagem[i][j] = 255;
				}
			}
		}
			
		
		return this.imagem;
	}
	
	public int[][] matrizDoubleToInt(double[][] imagem) throws Exception{
		int[][] imagemBinaria = new int[imagem.length][imagem[0].length];
		
		for (int i = 0; i < imagemBinaria.length; i++) {
			for (int j = 0; j < imagemBinaria[i].length; j++) {
				if(imagem[i][j] == 0.0){
					imagemBinaria[i][j] = 0;
				} else if(imagem[i][j] == 255.0){
					imagemBinaria[i][j] = 1;
				} else {
					System.err.println(imagem[i][j]);
					throw new Exception();
				}
			}
		}
		return imagemBinaria;
	}
	
	public double[][] getImagem() {
		return imagem;
	}

	public void setImagem(double[][] imagem) {
		this.imagem = imagem;
	}

	
}
