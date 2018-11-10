import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import us.lsi.common.Files2;
import us.lsi.common.Streams2;

//Correo profesora practica: anabsanchez@us.es
//Despacho provisional I0.63
public class pi {

	public static void main(String[] args) throws IOException, ParseException {
		
		FileReader f = new FileReader("src/imagenes/fichero.txt");
		FileReader f2 = new FileReader("src/imagenes/fich.txt");
     	//System.out.println(fechaOrdenada(f, LocalDate.of(2003, 10, 1) , LocalDate.now()));
     	//System.out.println(fechaOrdenada2(f,LocalDate.of(1990, 01, 10), LocalDate.now()));
        System.out.println(fechaOrdenadaStream(f, LocalDate.of(1990, 01, 10),  LocalDate.now()));
		//System.out.println(listNumEntero(f2));
		System.out.println(listNumEnteroStream(f2));
		//System.out.println(ListaFechasOrd(f));
		
	}
	//Función auxiliar
	public static List<LocalDate> ListaFechasOrd (FileReader f) throws IOException{
		List<LocalDate> res = new ArrayList<LocalDate>();
		BufferedReader br = new BufferedReader(f);
		String readLine = "";
		while ((readLine = br.readLine()) != null) {
			LocalDate df = LocalDate.parse(readLine, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			res.add(df);
		}
		Collections.sort(res);
		return res;
	}
	//Dado un fichero de texto con una fecha escrita en cada línea, genere otro fichero con
	//las fechas ordenadas y que estén entre dos fechas dadas	
	public static BufferedWriter fechaOrdenada (FileReader f, LocalDate d1, LocalDate d2) throws ParseException, IOException {
		 File fi = new File("src/imagenes/fichero1.txt");
		 FileWriter w = new FileWriter(fi);
		 BufferedWriter res = new BufferedWriter(w);
		 List<LocalDate> l = ListaFechasOrd(f);
		 for(int i=0;i<l.size();i++) {
			 LocalDate df = l.get(i);
			 if(d1.isBefore(df)) {
				 if(d2.isAfter(df)) {
					 res.write(df.toString());
					 res.flush();
					 res.newLine();
				 }
			 }
		 }
		res.close();
		return res;
	}
	// Funcion auxiliar segunda solucion
	public static List<LocalDate> entreFechas (FileReader f, LocalDate d1, LocalDate d2) throws IOException{
		List<LocalDate> res = new ArrayList<LocalDate>();
		BufferedReader br = new BufferedReader(f);
		String readLine = "";
		while ((readLine = br.readLine()) != null) {
			LocalDate df = LocalDate.parse(readLine, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			if(d1.isBefore(df) && d2.isAfter(df)) {
				res.add(df);
			}
		}
		return res;
	}
	// Segunda solucion iterativa
	public static BufferedWriter fechaOrdenada2 (FileReader f, LocalDate d1, LocalDate d2) throws ParseException, IOException {
		 File fi = new File("src/imagenes/fichero1.txt");
		 FileWriter w = new FileWriter(fi);
		 BufferedWriter res = new BufferedWriter(w);
		 List<LocalDate> l = entreFechas(f,d1,d2);
		 Collections.sort(l);
		 for(int i=0;i<l.size();i++) {
			 LocalDate df = l.get(i);
			 res.write(df.toString());
			 res.flush();
			 res.newLine();
		 }
		 res.close();
		return res;
	}

	//// Version Stream
	public static  Files2 fechaOrdenadaStream (FileReader f, LocalDate d1, LocalDate d2) throws IOException {
		List<LocalDate> l2 = entreFechas(f,d1,d2);
		Stream<LocalDate> l = l2.stream().sorted(Comparator.naturalOrder());
		
		return null;
	} 
	
	
	///////////////////////////////////////////////////////
	
	//Funcion auxiliar
	public static List<String> mapNumeros (FileReader f) throws IOException{
		BufferedReader br = new BufferedReader(f);
		String readLine = "";
		List<String> map = new ArrayList<String>();
		while((readLine = br.readLine()) != null) {
			for(int i=0; i<readLine.length(); i=i+2) {
				map.add(readLine.substring(i, i+1).trim());
			}
		}
		return map;
	}
	
	//Obtener una List<Integer> a partir de un fichero de texto que contiene en cada línea
	//una lista de números enteros separados por comas.
	public static List<Integer> listNumEntero (FileReader f) throws IOException{
		List<Integer> res = new ArrayList<Integer>();
		for(String s : mapNumeros(f)) res.add(Integer.valueOf(s)); 
		return res;
	}
	
	//Version stream
	public static List<Integer> listNumEnteroStream (FileReader f) throws IOException{
		List<Integer> res =  mapNumeros(f).stream()
				.flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
				.boxed().collect(Collectors.toList());
		return res;
	}
}