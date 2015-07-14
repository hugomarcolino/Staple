package combinacoes;

import java.util.ArrayList;
import java.util.List;

public class Staple {

	private List<int[][]> segmentacoes;
	
	private double[][] t0;
	private double[][] t1;

	private double[] p;
	private double[] q;
	
	private double[][] a;
	private double[][] b;
	private double[][] w;
	
	private double[][] imagem;
	
	public Staple(List<double[][]> listaSegmentacao) throws Exception{
		this.segmentacoes = new ArrayList<int[][]>();
		
		//Segmentações 
		for (double[][] segmentacao : listaSegmentacao) {
			this.segmentacoes.add(matrizDoubleToInt(segmentacao));
		}

		this.a = new double[segmentacoes.get(0).length][segmentacoes.get(0)[0].length];
		
		this.b = new double[segmentacoes.get(0).length][segmentacoes.get(0)[0].length];
		
		this.w = new double[segmentacoes.get(0).length][segmentacoes.get(0)[0].length];
		
		this.t0 = new double[segmentacoes.get(0).length][segmentacoes.get(0)[0].length];
		this.t1 = new double[segmentacoes.get(0).length][segmentacoes.get(0)[0].length];
		
		//Inicializar Pj com valores 0.9999
		this.p = new double[segmentacoes.size()];
		for (int j = 0; j < p.length; j++) {
			this.p[j] = 0.999;
		}
		
		//Inicializar Qj comm valores 0.9999
		this.q = new double[segmentacoes.size()];
		for (int j = 0; j < q.length; j++) {
			this.q[j] = 0.999;
		}
		
		//Inicializar f(Ti=1) e f(Ti=0)		
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				for (int[][] segmentacao : segmentacoes) {
					this.t1[i][j] += segmentacao[i][j];
				}
				this.t1[i][j] /= segmentacoes.size();
				this.t0[i][j] = 1 - this.t1[i][j];
			}
		}			
			  
	}
	
	public void iteracao(){
		
		//Calcular a e b
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = this.t1[i][j];
				b[i][j] = this.t0[i][j];
				for(int m = 0; m < segmentacoes.size(); m++){
					int[][] segmentacao = segmentacoes.get(m);
					if(segmentacao[i][j] == 1){
						a[i][j] *= p[m];
						b[i][j] *= (1-q[m]);
					} else {
						a[i][j] *= (1-p[m]);
						b[i][j] *= q[m];
					}
				}
			}
		}
		
		//Calcular variavel de peso w
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				w[i][j] = a[i][j]/(a[i][j]+b[i][j]); 
			}
		}
		
		//Calcular novos Pj e Qj
		for(int s = 0; s < segmentacoes.size(); s++){
			int[][] segmentacao = segmentacoes.get(s);
			double somatorioP = 0, somatorioQ = 0;
			double totalP = 0, totalQ = 0;
			for (int i = 0; i < segmentacao.length; i++) {
				for (int j = 0; j < segmentacao[i].length; j++) {		
					if(segmentacao[i][j] == 1){
						somatorioP += w[i][j];
					} else {
						somatorioQ += (1-w[i][j]);
					}
					totalP += w[i][j];
					totalQ += (1-w[i][j]);
				}
			}
			p[s] = somatorioP/totalP;
			q[s] = somatorioQ/totalQ;
		}
	}
	
	public int algoritmo(){
		
		int iteracoes = 1;
		
		double[][] pesoAnterior = cloneMatriz(this.w);
		this.iteracao();
		
		while(Math.abs(somaPesos(this.w) - somaPesos(pesoAnterior)) >= 0.01) {
			pesoAnterior = cloneMatriz(this.w);
			this.iteracao();
			iteracoes++;
		}
		 
		this.imagem = transformaImagem(pesoAnterior);
		
		return iteracoes;
	}
	
//	private boolean isNaN(double[][] matriz){	
//		for (int i = 0; i < matriz.length; i++) {
//			for (int j = 0; j < matriz[i].length; j++) {
//				if(((Double) matriz[i][j]).equals(Double.NaN)){
//					matriz[i][j] = 0.0;
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
	
	
	private double somaPesos(double[][] matriz){
		double somaPesos = 0.0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				somaPesos += matriz[i][j];
			}
		}
		
		return somaPesos;
	}
	
	public double[][] cloneMatriz(double[][] matriz){
		double[][] cloneMatriz = new double[matriz.length][matriz[0].length];
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				cloneMatriz[i][j] = matriz[i][j];
			}
		}
		
		return cloneMatriz;
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
	
	public double[][] transformaImagem(double[][] matriz) {
		double[][] imagem = new double[matriz.length][matriz[0].length];
		
		for (int i = 0; i < imagem.length; i++) {
			for (int j = 0; j < imagem[i].length; j++) {
				if(matriz[i][j] < 0.1){
					imagem[i][j] = 0.0;
				} else {
					imagem[i][j] = 255;
				}
			}
		}
		
		return imagem;
	}
	
	public double[][] getImagem() {
		return imagem;
	}

}