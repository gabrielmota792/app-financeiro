package fase1;

 	
	
public class Transacao {
	private static int contador = 1;
	private int id;
	private String descricao;
	private double valor;
	private String tipo; //"receita ou despesa"
	private String categoria;
	
	//novas transacoes
	public Transacao(String descricao, double valor, String tipo, String categoria) {
		this.id = contador++;
		this.descricao = descricao;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
	}
	
	//carregar arquivos 
	public Transacao(int id, String descricao, double valor, String tipo, 
String categoria) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.tipo = tipo;	
		this.categoria = categoria;
		
		if(id >= contador) {
			contador = id + 1;
		}
	}
	
	
	
	public int getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao =  descricao;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}



