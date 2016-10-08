package testes;

import java.util.ArrayList;
import java.util.List;

import combinacoes.Staple;
import combinacoes.StapleModificado;
import combinacoes.Votacao;
import util.Util;

public class Principal {
	
	public static final String caminho = "C:/Users/Hugo/SkyDrive/teste/resultados/Resultado_7/ph2/";

	public static void main(String[] args) throws Exception {
		
		for (int i = 1; i <= 200; i++) {
			if(i != 88 ){
				System.out.println("Imagem " +i);
				for (int limiar = 0; limiar <= 10; limiar++) {
					for (int sim = 1; sim <= 5; sim++) {
						double[][] imagem = Util.lerImagem(caminho+sim+"/limiarizacao/"+i+"_"+limiar+".bmp");
						if(imagem[288][378] != 255){							
							System.out.println(i+" "+limiar+" "+sim);
						}
					}
				}
				
			}
		}
		
	}
	
	public static void stapleLimiar() throws Exception{
		for (int i = 1; i <= 200; i++) {
			if(i != 88 ){
				System.out.println("Imagem " +i);
				
				for (int limiar = 0; limiar <= 10; limiar++) {
					List<double[][]> segmentacoes = new ArrayList<double[][]>();
					for (int sim = 1; sim <= 4; sim++) {
						segmentacoes.add(Util.lerImagem(caminho+sim+"/limiarizacao/"+i+"_"+limiar+".bmp"));
					}
					
					Staple staple = new Staple(segmentacoes);
					staple.algoritmo();
					Util.salvaImagem(caminho+"7/limiarizacao/"+i+"_"+limiar+".bmp", staple.getImagem());
				}
			}
		}
	}

	public static void stapleRede() throws Exception{
		for (int i = 1; i <= 200; i++) {
			if(i != 88 ){
				System.out.println("Imagem " +i);
				List<double[][]> segmentacoes = new ArrayList<double[][]>();
				
				for (int sim = 1; sim <= 5; sim++) {
					segmentacoes.add(Util.lerImagem(caminho+sim+"/final/"+i+".bmp"));	
				}
				
				StapleModificado staple = new StapleModificado(segmentacoes);
				staple.algoritmo();
				Util.salvaImagem(caminho+"7/final/"+i+".bmp", staple.getImagem());
				
			}
		}
	}
	
	public static void votacaoLimiar() throws Exception{
		for (int i = 1; i <= 200; i++) {
			if(i != 88 ){
				System.out.println("Imagem " +i);
				
				for (int limiar = 0; limiar <= 10; limiar++) {
					List<double[][]> segmentacoes = new ArrayList<double[][]>();
					for (int sim = 1; sim <= 5; sim++) {
						segmentacoes.add(Util.lerImagem(caminho+sim+"/limiarizacao/"+i+"_"+limiar+".bmp"));
					}
					
					Votacao votacao = new Votacao(segmentacoes);
					votacao.algoritmo();
					Util.salvaImagem(caminho+"8/limiarizacao/"+i+"_"+limiar+".bmp", votacao.getImagem());
				}
			}
		}
	}
}
