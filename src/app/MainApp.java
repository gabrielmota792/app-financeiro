package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import fase1.ControleFinanceiro;
import fase1.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainApp extends Application {
	
	private ControleFinanceiro controle = new ControleFinanceiro();
	private Transacao transacaoEditando = null;
	
	@Override	
	public void start(Stage primaryStage) {
		TableView<Transacao> tabela = new TableView<>();
		ObservableList<Transacao> dados = FXCollections.observableArrayList();
		dados.addAll(controle.getTransacoes());
		tabela.setItems(dados);
		
		TableColumn<Transacao, Integer> colId = new TableColumn<>("ID");
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<Transacao, String> colDescricao = new TableColumn<>("Descrição");
		colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		TableColumn<Transacao, Double> colValor = new TableColumn<>("Valor");
		colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		
		TableColumn<Transacao, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		
		TableColumn<Transacao, String> colCategoria = new TableColumn<>("Categoria");
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		
		
		tabela.getColumns().addAll(
		        colId,
		        colDescricao,
		        colValor,
		        colTipo,
		        colCategoria);
		
		Label lblDescricao = new Label("Descrição:");
		TextField txtDescricao = new TextField();
		
		Label lblValor = new Label("Valor:");
		TextField txtValor = new TextField();
		
		Label lblCategoria = new Label("Categoria:");
		TextField txtCategoria = new TextField();
		
		Label lblTipo = new Label("Tipo:");
		ComboBox<String> cbTipo = new ComboBox<>();
		
		cbTipo.getItems().addAll("Receita", "Despesa");
		cbTipo.setValue("Receita");
		
		Button btnAdicionar = new Button("Adicionar");
		Button btnEditar = new Button("Editar");
		Button btnSalvar = new Button("Salvar");
		Button btnRemover = new Button("Remover");
		
		btnAdicionar.setOnAction(e -> {
			try {
				String descricao = txtDescricao.getText();
				double valor = Double.parseDouble(txtValor.getText());
				String categoria = txtCategoria.getText();
				String tipo = cbTipo.getValue().toLowerCase();
				
				Transacao t = new Transacao(
						descricao,
						valor,
						tipo,
						categoria);
				
				controle.adicionarTransacao(t);
				dados.add(t);
				controle.salvarEmArquivo();
				System.out.println("Transação adicionada!");
				
				txtDescricao.clear();
				txtValor.clear();
				txtCategoria.clear();
			}catch(NumberFormatException ex) {
				System.out.println("Digite um valor válido!");
			}
			
		});
		btnEditar.setOnAction(e -> {
			Transacao selecionada = 
					tabela.getSelectionModel().getSelectedItem();
			if(selecionada != null) {
				transacaoEditando = selecionada;
				txtDescricao.setText(selecionada.getDescricao());
				txtValor.setText(String.valueOf(selecionada.getValor()));
				txtCategoria.setText(selecionada.getCategoria());
				cbTipo.setValue(selecionada.getTipo());
				System.out.println("Modo edição ativado");
			}
		});
		btnRemover.setOnAction(e -> {
			Transacao selecionada = tabela.getSelectionModel().getSelectedItem();
			if(selecionada != null) {
				dados.remove(selecionada);
				controle.getTransacoes().remove(selecionada);
				controle.salvarEmArquivo();
				System.out.println("Transação removida!");
			} else {
				System.out.println("Selecione uma transação!");
			}
		});
		btnSalvar.setOnAction(e -> {
			if(transacaoEditando != null) {
				try {
					transacaoEditando.setDescricao(txtDescricao.getText());
					transacaoEditando.setValor(Double.parseDouble(txtValor.getText()));
					transacaoEditando.setCategoria(txtCategoria.getText());
					transacaoEditando.setTipo(cbTipo.getValue().toLowerCase());
					tabela.refresh();
					controle.salvarEmArquivo();
					transacaoEditando = null;
					txtDescricao.clear();
					txtValor.clear();
					txtCategoria.clear();
					System.out.println("Transação atualizada!");
				} catch(NumberFormatException ex) {
					System.out.println("Valor inválido!");
				}
			}
		});
		
		HBox painelBotoes = new HBox(10);
		painelBotoes.getChildren().addAll(
				btnAdicionar,
				btnEditar,
				btnSalvar,
				btnRemover);
		
		VBox root = new VBox(10);
		
		root.getChildren().addAll(
				lblDescricao,
				txtDescricao,
				lblValor,
				txtValor,
				lblCategoria,
				txtCategoria,
				lblTipo,
				cbTipo,
				painelBotoes,
				tabela);
		
		Scene scene = new Scene(root, 500, 400);
		
		primaryStage.setTitle("Controle Financeiro");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
