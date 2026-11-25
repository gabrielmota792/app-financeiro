package fase1;

import java.util.Scanner;

public class ClassePrincipal {

	public static void main(String[] args) {
		// iniciar sistema
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		ControleFinanceiro controle = new ControleFinanceiro();
		
		while (true) {
			System.out.println("1 - Adicionar receita");
			System.out.println("2 - Adicionar despesa");
			System.out.println("3 - Ver saldo");
			System.out.println("4 - Listar transações");
			System.out.println("5 - Listar receitas");
			System.out.println("6 - Listar despesas");
			System.out.println("7 - Ver totais");
			System.out.println("8 - Ver resumo financeiro");
			System.out.println("9 - Remover transação");
			System.out.println("10 - Editar transação");
			System.out.println("11 - Ver extrato completo");
			System.out.println("12 - Filtrar por categoria");
			System.out.println("13 - Relatório por categoria");
			System.out.println("0 - Sair");
			
			int opcao = sc.nextInt();
			sc.nextLine();
			
			if(opcao == 1 || opcao == 2) {
				System.out.println("Descrição:");
				String desc = sc.nextLine();
				
				System.out.println("Valor:");
				double valor = sc.nextDouble();
				sc.nextLine();
				
				System.out.println("Categoria:");
				String categoria = sc.nextLine();
				
				String tipo = (opcao == 1) ? "receita" : "despesa";
				
				controle.adicionarTransacao(new Transacao(desc, valor, tipo, categoria));
				controle.salvarEmArquivo();
			} else if (opcao == 3) {
				System.out.println("Saldo: " + controle.calcularSaldo());
			} else if (opcao == 4) {
				controle.listarTransacoes();
			} else if (opcao == 5) {
				controle.listarPorTipo("receita");
			} else if (opcao == 6) {
				controle.listarPorTipo("despesa");
			} else if (opcao == 7) {
				System.out.println("Total receitas: " + controle.totalReceitas());
				System.out.println("Total despesas: " + controle.totalDespesas());
			} else if (opcao == 8){
				ResumoFinanceiro resumo = controle.gerarResumo();
				resumo.exibir();
			} else if (opcao == 9) {
				System.out.println("Digite o ID da transação:");
				int id = sc.nextInt();
				
				if (controle.removerTransacao(id)) {
					System.out.println("Transação removida com sucesso!");
					controle.salvarEmArquivo();
				} else {
					System.out.println("ID não encontrado.");
				}
			} else if (opcao == 10) {
			System.out.println("Digite o ID:");
			int id = sc.nextInt();
			sc.nextLine();
			
			System.out.println("Nova descrição:");
			String desc = sc.nextLine();
			
			System.out.println("Novo valor:");
			double valor = sc.nextDouble();
			sc.nextLine();
			
			System.out.println("Tipo (receita/despesa):");
			String tipo = sc.nextLine();
			
			if (controle.editarTransacao(id, desc, valor, tipo)) {
				System.out.println("Transação altualizada!");
				controle.salvarEmArquivo();
			} else {
				System.out.println("ID não encontrado.");
			}
			} else if (opcao == 11) {
				controle.extratoCompleto();
			} else if (opcao == 12) {
				System.out.println("Digite a categoria:");
				String cat = sc.nextLine();
				controle.listarPorCategoria(cat);
				
			} else if (opcao == 13) {
				controle.relatorioPorCategoria();
		
			} else if (opcao == 0) {
				controle.salvarEmArquivo();
				break;
			}
		}
	}
}

