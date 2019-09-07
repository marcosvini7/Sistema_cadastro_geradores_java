import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class Main {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static List<Gerador> geradores = new ArrayList<>();
	public static int id = 0;
	public static String localPadrao = "C:\\Users\\Irene\\Documents\\Sistema cadastro Gerador\\dados.txt";

	public static void main(String[] args) throws HeadlessException, ParseException {

		lerDados();
		ajustarID();
		menu();
	}

	public static void menu() throws HeadlessException, ParseException {
		try {
			int resposta = Integer.parseInt(JOptionPane.showInputDialog("------ Luiz do Gerador ------\n\n"
					+ "1 - Cadastrar Gerador \n2 - Listar Geradores \n3 - Listar Geradores Disponíveis \n4 - Listar Geradores Alugados"
					+ "\n5 - Listar Todos Geradores" + "\n6 - Configurações \n7 - Sair"));
			switch (resposta) {
			case 1:
				cadastrarGerador();
				break;

			case 2:
				listarGeradores();
				break;

			case 3:
				listarDisponiveis();
				break;

			case 4:
				listarAlugados();
				break;

			case 5:
				listarTodos();
				break;

			case 6:
				menuConfiguracao();
				break;

			case 7:
				mensagem();
				System.exit(0);

			default:
				JOptionPane.showMessageDialog(null, "Opção inválida!");
				menu();
			}
		} catch (Exception e) {
			mensagem();
			System.exit(0);
		}
	}

	public static void menuConfiguracao() throws HeadlessException, ParseException {
		try {
			int escolha = Integer.parseInt(JOptionPane.showInputDialog(
					"------ Configurações ------\n\n1 - Fazer Backup \n2 - Restaurar dados \n3 - Apagar todos os dados \n4 - Voltar"));
			switch (escolha) {
			case 1:
				backup();
				break;

			case 2:
				restaurar();
				break;

			case 3:
				apagar();
				break;

			case 4:
				menu();
				break;

			default:
				JOptionPane.showMessageDialog(null, "Opção inválida!");
				menuConfiguracao();
			}
		} catch (Exception e) {
			menu();
		}
	}

	public static void cadastrarGerador() throws HeadlessException, ParseException {
		try {
			String marca = JOptionPane.showInputDialog("Marca: ");
			verificarVazio(marca);
			String potencia = JOptionPane.showInputDialog("Potência: ");
			verificarVazio(potencia);
			Date ano = sdf.parse(JOptionPane.showInputDialog("Data de fabricação: "));
			double valorVenda = Double.parseDouble(JOptionPane.showInputDialog("Valor de venda: R$"));
			double valorAlguel = Double.parseDouble(JOptionPane.showInputDialog("Valor do aluguel: R$(Dia)"));
			boolean situacao1;
			int situacao = Integer
					.parseInt(JOptionPane.showInputDialog("Situação do gerador: " + "\n1 - Disponível \n2 - Alugado"));
			if (situacao == 1) {
				situacao1 = true;
			} else
				situacao1 = false;
			String descricao = JOptionPane.showInputDialog("Descrição: ");
			verificarVazio(potencia);
			if (situacao1 == true) {
				id++;
				geradores.add(new Gerador(id, marca, potencia, ano, situacao1, descricao, valorVenda, valorAlguel));
				JOptionPane.showMessageDialog(null, "Gerador Cadastrado!");
				salvarDados();
			} else {
				Date dataAluguel = sdf.parse(JOptionPane.showInputDialog("Data do aluguel: "));
				Date dataEntrega = sdf.parse(JOptionPane.showInputDialog("Data de entrega: "));
				id++;
				try {
					geradores.add(new GeradorAlugado(id, marca, potencia, ano, situacao1, descricao, valorVenda,
							valorAlguel, dataAluguel, dataEntrega));
					JOptionPane.showMessageDialog(null, "Gerador Cadastrado!");
					salvarDados();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					id--;
					menu();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos!");
			menu();
		}
		menu();
	}

	public static void verificarVazio(String item) throws HeadlessException, ParseException {
		if (item.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Dados inválidos!");
			menu();
		}
	}

	public static void listarGeradores() throws HeadlessException, ParseException {
		try {
			if (geradores.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Não há geradores cadastrados!");
				menu();
			} else {
				StringBuilder sb = new StringBuilder();
				for (Gerador gerador : geradores) {
					sb.append("Gerador " + gerador.getId() + "  Marca: " + gerador.getMarca() + "  Potência: "
							+ gerador.getPotencia() + "\n");
				}

				int escolha = Integer
						.parseInt(JOptionPane.showInputDialog(sb + "\n" + "Digite o índice para ver os detalhes: "));
				escolha--;
				JOptionPane.showMessageDialog(null, geradores.get(escolha));

			}
			listarGeradores();
		} catch (NumberFormatException e) {
			menu();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos!");
			menu();
		}
	}

	public static void listarTodos() throws HeadlessException, ParseException {
		StringBuilder sb = new StringBuilder();

		if (!geradores.isEmpty()) {
			for (Gerador gerador : geradores) {
				sb.append(gerador + "\n");
			}
			JOptionPane.showMessageDialog(null, sb);
		}
		if (geradores.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há geradores cadastrados!");
		}

		menu();
	}

	public static void listarDisponiveis() throws HeadlessException, ParseException {
		StringBuilder sb = new StringBuilder();
		boolean estaVazia = true;

		for (Gerador gerador : geradores) {
			if (gerador.isSituacao() == true) {
				sb.append("Gerador " + gerador.getId() + "  Marca: " + gerador.getMarca() + "  Potência: "
						+ gerador.getPotencia() + "\n");
				estaVazia = false;
			}
		}
		if (estaVazia == true) {
			JOptionPane.showMessageDialog(null, "Não há geradores disponíveis!");
		} else {
			JOptionPane.showMessageDialog(null, sb);
		}
		menu();
	}

	public static void listarAlugados() throws HeadlessException, ParseException {
		StringBuilder sb = new StringBuilder();
		boolean estaVazia = true;

		for (Gerador gerador : geradores) {
			if (gerador.isSituacao() == false) {
				sb.append("Gerador " + gerador.getId() + "  Marca: " + gerador.getMarca() + "  Potência: "
						+ gerador.getPotencia() + "\n");
				estaVazia = false;
			}
		}
		if (estaVazia == true) {
			JOptionPane.showMessageDialog(null, "Não há geradores Alugados!");
		} else {
			JOptionPane.showMessageDialog(null, sb);
		}
		menu();
	}

	public static void mensagem() {
		Date agora = new Date();
		if (agora.getHours() < 5) {
			JOptionPane.showMessageDialog(null, "Boa madrugada!");
		} else if (agora.getHours() < 12) {
			JOptionPane.showMessageDialog(null, "Bom dia!");
		} else if (agora.getHours() < 19) {
			JOptionPane.showMessageDialog(null, "Boa tarde!");
		} else
			JOptionPane.showMessageDialog(null, "Boa noite!");
	}

	public static void salvarDados() throws HeadlessException, ParseException {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(localPadrao))) {
			for (Gerador gerador : geradores) {
				bw.write(gerador.print() + "\n");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Falha ao salvar dados!");
		}
	}

	public static void lerDados() throws HeadlessException, ParseException {

		try (BufferedReader br = new BufferedReader(new FileReader(localPadrao))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] gerador = linha.split(",");
				boolean situacao;
				if ((gerador[4]).equals("false")) {
					situacao = false;
				} else {
					situacao = true;
				}
				if (situacao == true) {
					geradores.add(new Gerador(Integer.parseInt(gerador[0]), gerador[1], gerador[2],
							sdf.parse(gerador[3]), situacao, gerador[5], Double.parseDouble(gerador[6]),
							Double.parseDouble(gerador[7])));
				} else {
					geradores.add(new GeradorAlugado(Integer.parseInt(gerador[0]), gerador[1], gerador[2],
							sdf.parse(gerador[3]), situacao, gerador[5], Double.parseDouble(gerador[6]),
							Double.parseDouble(gerador[7]), sdf.parse(gerador[8]), sdf.parse(gerador[9])));
				}
				linha = br.readLine();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Falha ao recuperar dados!");
			menu();
		}

	}

	public static void backup() throws HeadlessException, ParseException {
		if (geradores.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há dados para realizar o Backup!");
			menuConfiguracao();
		}
		String local = JOptionPane.showInputDialog("Digite o endereço onde deseja fazer o Backup: ");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(local + "\\dados.txt"))) {
			for (Gerador gerador : geradores) {
				bw.write(gerador.print() + "\n");
			}
		} catch (Exception e) {
			if (local.isEmpty()) {
				menuConfiguracao();
			} else {
				JOptionPane.showMessageDialog(null, "Endereço não encontrado!");
				menuConfiguracao();
			}
		}
		JOptionPane.showMessageDialog(null, "Backup realizado com sucesso!");
		menuConfiguracao();
	}

	public static void restaurar() throws HeadlessException, ParseException {
		if (!geradores.isEmpty()) {
			try {
				int n = Integer.parseInt(JOptionPane
						.showInputDialog("Seus dados atuais serão perdidos!\nDeseja continuar? \n1 - Sim \n2 - Não"));
				if (n != 1) {
					menuConfiguracao();
				}
			} catch (NumberFormatException e) {
				menuConfiguracao();
			}
		}
		String local = JOptionPane.showInputDialog("Digite o endereço do arquivo de Restauração: ");
		try (BufferedReader br = new BufferedReader(new FileReader(local))) {
			geradores.clear();
			String linha = br.readLine();
			while (linha != null) {
				String[] gerador = linha.split(",");
				boolean situacao;
				if ((gerador[4]).equals("false")) {
					situacao = false;
				} else {
					situacao = true;
				}
				if (situacao == true) {
					geradores.add(new Gerador(Integer.parseInt(gerador[0]), gerador[1], gerador[2],
							sdf.parse(gerador[3]), situacao, gerador[5], Double.parseDouble(gerador[6]),
							Double.parseDouble(gerador[7])));
					salvarDados();
				} else {
					geradores.add(new GeradorAlugado(Integer.parseInt(gerador[0]), gerador[1], gerador[2],
							sdf.parse(gerador[3]), situacao, gerador[5], Double.parseDouble(gerador[6]),
							Double.parseDouble(gerador[7]), sdf.parse(gerador[8]), sdf.parse(gerador[9])));
					salvarDados();
				}
				linha = br.readLine();
			}

		} catch (Exception e) {
			if (local.isEmpty()) {
				menuConfiguracao();
			} else {
				JOptionPane.showMessageDialog(null, "Arquivo não encontrado!");
				menuConfiguracao();
			}
		}

		JOptionPane.showMessageDialog(null, "Restauração realizada com sucesso!");
		menuConfiguracao();
	}

	public static void apagar() throws HeadlessException, ParseException {
		if (!geradores.isEmpty()) {
			try {
				int n = Integer.parseInt(JOptionPane
						.showInputDialog("Todos os seus dados serão perdidos!\nDeseja continuar? \n1 - Sim \n2 - Não"));
				if (n == 1) {
					geradores.clear();
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(localPadrao))) {
						bw.write("");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Não foi possível apagar os dados!");
						menuConfiguracao();
					}
					JOptionPane.showMessageDialog(null, "Operação bem sucedida!");
					menuConfiguracao();
				}
			} catch (NumberFormatException e) {
				menuConfiguracao();
			}
		} else
			JOptionPane.showMessageDialog(null, "Não há dados salvos!");
		menuConfiguracao();
	}

	public static void ajustarID() {
		if (!geradores.isEmpty()) {
			int n = geradores.size() - 1;
			id = geradores.get(n).getId();

		}
	}
}
