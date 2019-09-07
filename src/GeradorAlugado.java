import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GeradorAlugado extends Gerador {
	private Date dataAluguel;
	private Date dataEntrega;
	private int tempo;

	public GeradorAlugado() {
		super();
	}

	public GeradorAlugado(int id, String marca, String potencia, Date ano, boolean situacao, String descricao,
			double valorVenda, double valorAluguel, Date dataAluguel, Date dataEntrega) throws Exception {
		super(id, marca, potencia, ano, situacao, descricao, valorVenda, valorAluguel);

		if (dataAluguel.after(dataEntrega)) {
			throw new Exception("As datas devem ser futuras!");
		} else {
			this.dataAluguel = dataAluguel;
			this.dataEntrega = dataEntrega;
		}

		long duracao = dataEntrega.getTime() - dataAluguel.getTime();
		tempo = (int) TimeUnit.DAYS.convert(duracao, TimeUnit.MILLISECONDS);
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public int getTempo() {
		return tempo;
	}

	public double valorTotal() {
		long duracao = dataEntrega.getTime() - dataAluguel.getTime();
		double dias = TimeUnit.DAYS.convert(duracao, TimeUnit.MILLISECONDS);

		return valorAluguel * dias;
	}

	@Override
	public String toString() {
		return super.toString() + "\nData do aluguel: " + sdf.format(dataAluguel) + "\nData de entrega: "
				+ sdf.format(dataEntrega) + "\nTempo estimado: " + tempo + " Dias" + "\nValor: R$ "
				+ String.format("%.2f", valorTotal());
	}

	@Override
	public String print() {
		return super.print() + "," + sdf.format(dataAluguel) + "," + sdf.format(dataEntrega);
				
	}

}
