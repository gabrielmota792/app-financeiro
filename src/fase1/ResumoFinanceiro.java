package fase1;

public class ResumoFinanceiro {
	
	private double receitas;
	private double despesas;
	private double saldo;
	
	public ResumoFinanceiro(double receitas, double despesas) {
		this.receitas = receitas;
		this.despesas = despesas;
		this.saldo = receitas - despesas;
	}
	
	public void exibir() {
		System.out.println("Receitas: " + receitas);
		System.out.println("Despesas: " + despesas);
		System.out.println("Sobra: " + saldo);
	}

}
