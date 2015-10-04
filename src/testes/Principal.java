package testes;

import java.util.ArrayList;
import java.util.List;

import combinacoes.Media;
import combinacoes.Staple;
import combinacoes.StapleModificado;
import combinacoes.Votacao;
import util.Util;

public class Principal {
	
	public static final String caminhoVessel = "C:/Users/Hugo/Documents/Dropbox/Mestrado de Computação/Projeto/Teste/Vessel [bmp]/";
	public static final String caminhoORLFaces = "C:/Users/Hugo/Documents/Dropbox/Mestrado de Computação/Projeto/Teste/orl_faces [bmp]/";
	public static final String caminhoBerkeley = "C:/Users/Hugo/Documents/Dropbox/Mestrado de Computação/Projeto/Teste/Berkeley [bmp]/";

	public static void main(String[] args) throws Exception {
		
		staple();
		
	}
	
	public static void staple() throws Exception{
		for (int i = 1; i <= 20; i++) {
			System.out.println("Imagem " +i+":");
			double[][] imagem = null;
			double[][] imagemResposta = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/gt.gif"));
			double[][] mask = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/mask.gif"));
			
			List<double[][]> segmentacoes = new ArrayList<double[][]>();
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/1.bmp"));		
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/2.bmp"));
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/3.bmp"));
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/4.bmp"));
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/5.bmp"));
			segmentacoes.add(imagem);
			
			//imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/6.bmp"));				
			//segmentacoes.add(imagem);
			
			StapleModificado stapleModificado = new StapleModificado(segmentacoes);
			stapleModificado.algoritmo();
			acerto(stapleModificado.getImagem(0.5), imagemResposta, mask, "Staple Modificado:");
			
			Staple staple = new Staple(segmentacoes);
			staple.algoritmo();
			Util.salvaImagem(caminhoVessel+"Results/"+i+"/staple.bmp", staple.getImagem());
			acerto(staple.getImagem(), imagemResposta, mask, "Staple:");
			
			Votacao votacao = new Votacao(segmentacoes);
			votacao.algoritmo();
			Util.salvaImagem(caminhoVessel+"Results/"+i+"/votacao.bmp", votacao.getImagem());
			acerto(votacao.getImagem(), imagemResposta, mask, "Votação:");
			

			System.out.println("======================");
			
		}
	}
	
	
	public static void stapleModificadoTextura() throws Exception{

		String caminho = caminhoBerkeley;
		//String caminho = caminhoVessel;
		
		for (int i = 5; i <= 5; i++) {
			System.out.println("Imagem " +i+":");
			double[][] imagem = null;
			List<double[][]> segmentacoes = new ArrayList<double[][]>();
			
			for (int j = 1; j <= 5; j++) {
				System.out.println(caminho+"Results/"+i+"/textura/"+j+".bmp");
				imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+"Results/"+i+"/textura/"+j+".bmp"));
				segmentacoes.add(imagem);
			}
			
			StapleModificado stapleModificado = new StapleModificado(segmentacoes);
			stapleModificado.algoritmo();
			Util.salvaImagem(caminho+"Results/"+i+"/textura/stapleModificado.bmp", stapleModificado.getImagem(0.4));

			System.out.println("======================");
			
		}
	}
	
	public static void stapleModificadoNiblackVessel() throws Exception{

		for (int i = 1; i <= 20; i++) {
			System.out.println("Imagem " +i+":");
			double[][] imagem = null;
			double[][] imagemResposta = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/gt.gif"));
			double[][] mask = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+i+"/mask.gif"));
			List<double[][]> segmentacoes = new ArrayList<double[][]>();
			
			for (int j = 1; j <= 30; j++) {
				imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminhoVessel+"Results/"+i+"/niblack/"+j+".bmp"));				
				segmentacoes.add(imagem);
			}
			
			StapleModificado stapleModificado = new StapleModificado(segmentacoes);
			stapleModificado.algoritmo();
			double[][] imagemR = stapleModificado.getImagem(0.5);
			Util.salvaImagem(caminhoVessel+"Results/"+i+"/stapleModificado.bmp", imagemR);
			acerto(imagemR, imagemResposta, mask, "Staple:");

			System.out.println("======================");
			
		}
	}

	
	public static double acerto(double[][] imagem, double[][] imagemResultado, double[][] mask, String string){
		double vasoCandidato = 0;
		double vasoEncontrado = 0;
		double vasoReal = 0;
		double acerto = 0; 
		double pixels = 0;
		
		for(int i=0; i<imagem.length; i++){
			for(int j=0; j<imagem[i].length; j++){
				if (mask[i][j] != 0.0) {					
					if(imagem[i][j] == imagemResultado[i][j]){
						acerto++;
					}
					if(imagem[i][j] == 255){
						vasoCandidato++;
						if(imagemResultado[i][j] == 255) {
							vasoEncontrado++;
						}
					}
					if(imagemResultado[i][j] == 255) {
						vasoReal++;
					}
					pixels++;
				}
			}
		}
		
		System.out.println(string);
		System.out.println("Acerto: "+acerto/pixels);
		System.out.println("Precisao: "+(vasoEncontrado/vasoCandidato));
		System.out.println("Cobertura: "+(vasoEncontrado/vasoReal)+"\n");	
		
		return acerto/pixels;
	}
	
	public static void testeFaces() throws Exception{
		for (int i = 1; i <= 40; i++) {
			System.out.println("Pasta " +i);
			double[][] imagem = null;
			List<double[][]> imagens = new ArrayList<double[][]>();
			
			for (int nImagem = 6; nImagem <= 10; nImagem++) {
				imagem = Util.lerImagem(caminhoORLFaces+"s"+i+"/"+ nImagem +".bmp");				
				imagens.add(imagem);
			}
							
			StapleModificado stapleModificado = new StapleModificado(imagens);
			stapleModificado.algoritmo();
			Util.salvaImagem(caminhoORLFaces+"combinacaoStaple/"+i+".bmp", stapleModificado.getImagem(0.5));
			
			Media media = new Media(imagens);
			media.calcularMedia();
			Util.salvaImagem(caminhoORLFaces+"combinacaoMedia/"+i+".bmp", media.getImagem());
							
		}
	}
}
