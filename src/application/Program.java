package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		List<Sale> list = new ArrayList<>();

		System.out.println("Entre com o caminho do arquivo:");
		String path = sc.next();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] field = line.split(",");
				int month = Integer.parseInt(field[0]);
				int year = Integer.parseInt(field[1]);
				String seller = field[2];
				int items = Integer.parseInt(field[3]);
				double total = Double.parseDouble(field[4]);
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio: ");
			List<Sale> numbers = list.stream()
					.filter(p -> p.getYear() == 2016)
					.sorted(Comparator.comparing(Sale::averagePrice).reversed())
					.limit(5)
					.collect(Collectors.toList());
			numbers.forEach(System.out::print);

			System.out.print("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = ");			
			double value = list.stream()
					.filter(p -> p.getSeller().equals("Logan") 
					&& (p.getMonth().equals(1) || p.getMonth().equals(7)))
					.map(p -> p.getTotal())
					.reduce(0.0, (x1, x2) -> x1+x2);			
			System.out.println(value);								

		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();

	}

}
