import java.text.SimpleDateFormat;
import java.util.Date;

public class Gerador {
	private int id;
	private String marca;
	private String potencia;
	private Date ano;
	private double valorVenda;
	protected double valorAluguel;
	private boolean situacao;
	private String descricao;

	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Gerador() {

	}

	public Gerador(int id, String marca, String potencia, Date ano, boolean situacao, String descricao,
			double valorVenda, double valorAluguel) {

		this.id = id;
		this.marca = marca;
		this.potencia = potencia;
		this.ano = ano;
		this.situacao = situacao;
		this.descricao = descricao;
		this.valorVenda = valorVenda;
		this.valorAluguel = valorAluguel;
	}

	public int getId() {
		return id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPotencia() {
		return potencia;
	}

	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public double getValorAluguel() {
		return valorAluguel;
	}

	public void setValorAluguel(double valorAluguel) {
		this.valorAluguel = valorAluguel;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		String situacao1;
		if (situacao == true) {
			situacao1 = "Disponível";
		} else
			situacao1 = "Alugado";

		return "Gerador " + id + "\n\nMarca: " + marca + "\nPotência: " + potencia + "\nData de fabricão: "
				+ sdf.format(ano) + "\nValor de venda: " + valorVenda + "\nValor de aluguel: R$ " + valorAluguel
				+ "\nSituação: " + situacao1 + "\nDescrição: " + descricao;

	}

	public String print() {
		String situacao1;
		if (situacao == true) {
			situacao1 = "Disponível";
		} else
			situacao1 = "Alugado";

		return id + "," + marca + "," + potencia + "," + sdf.format(ano) + "," + situacao + "," + descricao + ","
				+ valorVenda + "," + valorAluguel;
	}

}
