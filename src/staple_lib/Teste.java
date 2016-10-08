package staple_lib;
import org.itk.simple.*;

public class Teste {

	public static final String caminho = "C:/Users/Hugo/Desktop/Teste/Vessel [jpg]/";
	
	public static void main(String[] args){
		
		teste_1();
		//teste_2();
	}
	
	 public static void teste_1() {

		    String caminhoLer = "C:/Users/Hugo/Desktop/teste.jpg";
		    String caminhoEscrever = "C:/Users/Hugo/Desktop/testeEscrita.jpg";

		    Image image = SimpleITK.readImage(caminhoLer);
		    
		    System.out.println("Dimensões: "+ image.getWidth() + " " + image.getHeight());
		    
		    image = SimpleITK.binaryErode(image);
		    
		    SimpleITK.writeImage(image, caminhoEscrever);
		    
		  }
	
	public static void teste_2() throws Exception{
		
		for (int i = 1; i <= 20; i++) {
			System.out.println("Imagem " +i+":");
			
			Image imagem = null;
			double[][] imagemResposta = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/gt.gif"));
			double[][] mask = Util.retornaImagemCinza(Util.lerImagemColorida(caminho+i+"/mask.gif"));
			
			VectorOfImage segmentacoes = new VectorOfImage();
			
			imagem = SimpleITK.readImage(caminho+i+"/1.jpg");
			segmentacoes.push_back(imagem);
			
			imagem = SimpleITK.readImage(caminho+i+"/2.jpg");
			segmentacoes.push_back(imagem);
			
			imagem = SimpleITK.readImage(caminho+i+"/3.jpg");
			segmentacoes.push_back(imagem);
			
			imagem = SimpleITK.readImage(caminho+i+"/4.jpg");
			segmentacoes.push_back(imagem);
			
			imagem = SimpleITK.readImage(caminho+i+"/5.jpg");
			segmentacoes.push_back(imagem);
			
			STAPLEImageFilter staple = new STAPLEImageFilter();
			
			imagem = staple.execute(segmentacoes, 1, 255.0, 1000);
			//image = SimpleITK.sTAPLE(segmentacoes, 1, 255.0);
			
			imagem = SimpleITK.rescaleIntensity(imagem, 0, 255);
			imagem = SimpleITK.cast(imagem, PixelIDValueEnum.sitkUInt8);
			
			SimpleITK.writeImage(imagem, caminho+i+"/staple-lib.jpg");
			
			double[][] imagemStaple = limiarizacao(Util.lerImagem(caminho+i+"/staple-lib.jpg"), 127);
			acerto(imagemStaple, imagemResposta, mask);
			//Util.salvaImagem(caminho+i+"/staple-lib.jpg", imagemStaple);
			
			System.out.println("======================");
			
		}
	}
	
	public static void acerto(double[][] imagem, double[][] imagemResultado, double[][] mask){
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
		
		System.out.println("Acerto: "+acerto/pixels);
		System.out.println("Precisao: "+(vasoEncontrado/vasoCandidato));
		System.out.println("Cobertura: "+(vasoEncontrado/vasoReal)+"\n");	
	}
	
	public static double[][] limiarizacao(double[][] imagem, double limiar){
		
		for(int i=0; i<imagem.length; i++){
			for(int j=0; j<imagem[i].length; j++){
				if (imagem[i][j] < limiar) {					
					imagem[i][j] = 0;
				} else {
					imagem[i][j] = 255;
				}
			}
		}
		
		return imagem;
	}
}
