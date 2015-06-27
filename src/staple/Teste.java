package staple;

import java.util.ArrayList;
import java.util.List;

import staple.Staple;

public class Teste {
	
	public static final String caminho = "C:/Users/Hugo/Desktop/Teste/Vessel/";
	
	public static void main(String[] args) throws Exception{
		for (int i = 1; i <= 1; i++) {
			System.out.println("Imagem " +i+":");
			
			double[][] imagemResposta = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/gt.gif"));
			double[][] mask = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/mask.gif"));
			List<double[][]> segmentacoes = new ArrayList<double[][]>();
			
			for (int j = 1; j <= 121; j++) {				
				segmentacoes.add(Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/"+j+".bmp")));
			}
			
			Staple staple = new Staple(segmentacoes);
			staple.algoritmo();
			Util.salvaImagem(caminho+i+"/staple.bmp", staple.getImagem());
			acerto(staple.getImagem(), imagemResposta, mask, "Staple:");
			
			Votacao votacao = new Votacao(segmentacoes);
			votacao.algoritmo();
			Util.salvaImagem(caminho+i+"/votacao.bmp", votacao.getImagem());
			acerto(votacao.getImagem(), imagemResposta, mask, "Votação:");

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
