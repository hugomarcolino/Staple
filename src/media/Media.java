package media;

import java.util.ArrayList;
import java.util.List;

public class Media {
	
	private List<double[][]> listaImagens;
	
	private double[][] imagem;
	
	public Media (List<double[][]> listaImagens) {
		this.listaImagens = new ArrayList<double[][]>();
		
		//Imagens Normalizadas 
		for (double[][] imagem : listaImagens) {
			this.listaImagens.add(imagem);
		}
	}
	
	public void calcularMedia(){
		double[][] imagemMedia = new double[this.listaImagens.get(0).length][this.listaImagens.get(0)[0].length];
		
		for (int i = 0; i < imagemMedia.length; i++) {
			for (int j = 0; j < imagemMedia[i].length; j++) {
				for (int imagem = 0; imagem < this.listaImagens.size(); imagem++) {
					imagemMedia[i][j] += this.listaImagens.get(imagem)[i][j];
				}
				imagemMedia[i][j] /= this.listaImagens.size();
			}
		}
		
		this.imagem = imagemMedia;
	}
	
	public double[][] getImagem() {
		return imagem;
	}

}
