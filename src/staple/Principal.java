package staple;

import java.util.ArrayList;
import java.util.List;

import staple.Staple;

public class Principal {
	
	public static final String caminho = "C:/Users/Hugo/Documents/Dropbox/Mestrado de Computa��o/Projeto/Teste/vessel [bmp]/";
	
	public static void main(String[] args) throws Exception{
		for (int i = 1; i <= 20; i++) {
			System.out.println("Imagem " +i+":");
			double[][] imagem = null;
			double[][] imagemResposta = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/gt.gif"));
			double[][] mask = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/mask.gif"));
			
			List<double[][]> segmentacoes = new ArrayList<double[][]>();
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/1.bmp"));				
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/2.bmp"));				
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/3.bmp"));				
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/4.bmp"));				
			segmentacoes.add(imagem);
			
			imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/5.bmp"));				
			segmentacoes.add(imagem);
			
			//imagem = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/6.bmp"));				
			//segmentacoes.add(imagem);
			
			Staple staple = new Staple(segmentacoes);
			staple.algoritmo();
			Util.salvaImagem(caminho+"Results/"+i+"/staple.bmp", staple.getImagem());
			acerto(staple.getImagem(), imagemResposta, mask, "Staple:");
			
			Votacao votacao = new Votacao(segmentacoes);
			votacao.algoritmo();
			Util.salvaImagem(caminho+"Results/"+i+"/votacao.bmp", votacao.getImagem());
			acerto(votacao.getImagem(), imagemResposta, mask, "Vota��o:");

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
}
