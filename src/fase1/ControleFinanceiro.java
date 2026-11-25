package fase1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControleFinanceiro {
	
	private ArrayList<Transacao> transacoes = new ArrayList<>();
	public ArrayList<Transacao> getTransacoes(){
		return transacoes;
	}
	public void adicionarTransacao(Transacao t) {
		transacoes.add(t);
	}
	
	public double calcularSaldo() {
		double saldo = 0;
		for (Transacao t : transacoes) {
			if(t.getTipo().equals("receita")) {
			saldo += t.getValor();
		} else {
			saldo -= t.getValor();
		}
	} 
		return saldo;
	}
	
	public void listarTransacoes() {
		if(transacoes.isEmpty()) {
			System.out.println("Nenhuma transação encontrada.");
		return;
		}
		
		for (Transacao t : transacoes) {
			System.out.println(
					"ID: " + t.getId()+
					"Descrição: " + t.getDescricao()+
					" | Valor: " + t.getValor()+
					" | Tipo: " + t.getTipo()
					);
		}
	}
	
	public void listarPorTipo(String tipo) {
		boolean encontrou = false;
		
		for (Transacao t : transacoes) {
			if(t.getTipo().equalsIgnoreCase(tipo)) {
				System.out.println(
						"Descrição: " + t.getDescricao()+
						" | Valor: " + t.getValor()
						);
				encontrou = true;
			}
		}
		
		if(!encontrou) {
			System.out.println("Nenhuma transação do tipo " + tipo);
		}
	}
	
	public double totalReceitas() {
		double total = 0;
		
		for(Transacao t : transacoes) {
			if(t.getTipo().equalsIgnoreCase("receita")) {
				total += t.getValor();
			}
		}
		return total;
	}
	
	public double totalDespesas() {
		double total = 0;
		
		for (Transacao t : transacoes) {
			if(t.getTipo().equalsIgnoreCase("despesa")) {
				total += t.getValor();
			}
		}
		return total;
	}
	
	public void salvarEmArquivo() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("transacoes.txt"));
			
			for (Transacao t : transacoes) {
				writer.println(
								t.getId() + ";" +
								t.getDescricao() + ";" +
								t.getValor() + ";" +
								t.getTipo() + ";" +
								t.getCategoria()
								);
			}
			
			writer.close();
			System.out.println("Dados salvos com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao salvar arquivo.");
		}
	}
	
	public void carregarDoArquivo() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("transacoes.txt"));
			String linha;
			
			while ((linha = reader.readLine()) != null) {
				if (linha.trim().isEmpty()) continue;
				String[] partes = linha.split(";");
				if (partes.length == 5) {
				
				int id = Integer.parseInt(partes[0]);
				String descricao = partes[1];
				double valor = Double.parseDouble(partes[2]);
				String tipo = partes[3];
				String categoria = partes[4];
				
				Transacao t = new Transacao(id, descricao, valor, tipo, categoria);
				transacoes.add(t);
				}
			}
			reader.close();
		}catch (IOException e) {
			System.out.println("Arquivo não encontrado. Será criado ao salvar.");
		}
	}
	
	public void mostrarResumo() {
		double receitas = totalReceitas();
		double despesas = totalDespesas();
		double saldo = calcularSaldo();
		
		System.out.println("\n===== RESUMO FINANCEIRO =====");
		System.out.println("Total de receitas: " + receitas);
		System.out.println("Total de despesas: " + despesas);
		System.out.println("Saldo (sobra): " + saldo);
	}
	
	public ResumoFinanceiro gerarResumo() {
		return new ResumoFinanceiro(totalReceitas(), totalDespesas());
	}
	
	public boolean removerTransacao(int id) {
		return transacoes.removeIf(t -> t.getId() == id);
	}
	
	public boolean editarTransacao(int id, String novaDescricao, double novoValor, String novoTipo) {
		for (Transacao t : transacoes) {
			if (t.getId() == id) {
				t.setDescricao(novaDescricao);
				t.setValor(novoValor);
				t.setTipo(novoTipo);
				return true;
			}
		}
		return false;
	}
	
	public void extratoCompleto() {
		if (transacoes.isEmpty()) {
			System.out.println("Nenhuma transação encontrada.");
			return;
		}
		
		System.out.println("\n===== EXTRATO FINANCEIRO =====\n");
		transacoes.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
		for (Transacao t : transacoes) {
			String sinal = t.getTipo().equalsIgnoreCase("receita") ? "+" : "-";
			System.out.printf("ID: %d | %-10s | %s%.2f | %s\n",
					t.getId(),
					t.getDescricao(),
					sinal,
					t.getValor(),
					t.getTipo().toUpperCase()
					);
		System.out.println("\n--------------------------------");
		System.out.println("Total receitas: " + totalReceitas());
		System.out.println("Total despesas: " + totalDespesas());
		System.out.println("Saldo: " + calcularSaldo());
	}
	}
	
	public void listarPorCategoria(String categoria) {
		boolean encontrou = false;
		for (Transacao t : transacoes) {
			if(t.getCategoria().equalsIgnoreCase(categoria)) {	
				String sinal = t.getTipo().equalsIgnoreCase("receita") ? "+" : "-";
				System.out.println(
						"ID: " + t.getId()+
						" | " + t.getDescricao()+
						" | " + sinal + t.getValor()+
						" | " + t.getTipo()
						);
				encontrou = true;}
			}
		if (!encontrou) {	
			System.out.println("Nenhuma transação nessa categoria.");
		}
	}
	
	public void relatorioPorCategoria() {
		Map<String, Double> mapa = new HashMap<>();
		for (Transacao t : transacoes) {
			double valor = t.getTipo().equalsIgnoreCase("receita")
					? t.getValor()
					: -t.getValor();
			mapa.put(
					t.getCategoria(),
					mapa.getOrDefault(t.getCategoria(), 0.0) + valor
					);
			}
		System.out.println("\n===== RELATÓRIO POR CATEGORIA =====\n");
		for (String cat: mapa.keySet()) {
			System.out.println(cat + ": " + mapa.get(cat));
		}
	}
}
	


