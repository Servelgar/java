import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class pirecursivo {

	public static void main(String[] args) throws IOException {
		String f = "src/imagenes/fichero.txt";
		String f2 = "src/imagenes/fich.txt";
		//List<LocalDate> l = new ArrayList<LocalDate>();
		// List<String> Lineas = new ArrayList<String>();
		//Lineas.addAll(Files2.getLines(f));
		//System.out.println(fechaOrdenadaRec(f,LocalDate.of(1996, 12, 12), LocalDate.now()));
		System.out.println(listNumEnteroRec(f2));
	}
	
	//Dado un fichero de texto con una fecha escrita en cada línea, genere otro fichero con
	//las fechas ordenadas y que estén entre dos fechas dadas	
	public static BufferedWriter fechaOrdenadaRec (String f, LocalDate d1, LocalDate d2) throws IOException {
		 File fi = new File("src/imagenes/fichero1.txt");
		 FileWriter w = new FileWriter(fi);
		 List<LocalDate> l = new ArrayList<LocalDate>();
		 List<String> Lineas = new ArrayList<String>();
		 Lineas.addAll(Files2.getLines(f));
		return fechaOrdenadaRec(d1,d2,entreFechasRec(d1,d2,l,Lineas,0),l,Lineas,0,w);
	}
	public static BufferedWriter fechaOrdenadaRec (LocalDate d1, LocalDate d2, List<LocalDate> entreFechas, List<LocalDate> l,
			List<String> Lineas,
			Integer i, FileWriter w) throws IOException {
		BufferedWriter res = new BufferedWriter(w);
		if(i >= entreFechas.size()) {
			res.close();
		} else {
			LocalDate d = entreFechas.get(i);
			if(i != 0) res.newLine();
			res.write(d.toString());
			res.flush(); 
			i++;
			res = fechaOrdenadaRec(d1,d2,entreFechas,l,Lineas,i,w);
		}
		return res;
	}
	public static List<LocalDate> entreFechasRec (LocalDate d1, LocalDate d2, List<LocalDate> l, List<String> Lineas, 
			Integer c) throws IOException{
		List<LocalDate> res = new ArrayList<LocalDate>();
		if (c >= Lineas.size())  {
			res.addAll(l);
			Collections.sort(res);
		} else {
			String readLine = Lineas.get(c);
			LocalDate df = LocalDate.parse(readLine, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			if(d1.isBefore(df) && d2.isAfter(df)) {
				l.add(df);
			}
			c ++;
			res = entreFechasRec(d1,d2,l,Lineas,c);
		}
		return res;
	}
	
	//Obtener una List<Integer> a partir de un fichero de texto que contiene en cada línea
	//una lista de números enteros separados por comas.
	
	public static List<Integer> listNumEnteroRec(String f){
		List<Integer> prov2 = new ArrayList<Integer>();
		List<String> prov = new ArrayList<String>();
		List<String> lineas = new ArrayList<String>();
		lineas.addAll(Files2.getLines(f));
		return listNumEnteroRec(listNumEnteroRecAux(lineas,prov,0,0),prov2,0);
	}
	public static List<Integer> listNumEnteroRec(List<String> numeros,List<Integer> prov, Integer i){
		List<Integer> res = new ArrayList<Integer>();
		if (i >= numeros.size()) {
			res.addAll(prov);
		} else {
			prov.add(Integer.valueOf(numeros.get(i)));
			i++;
			res = listNumEnteroRec(numeros,prov,i);
		}
		return res;
	}
	public static List<String> listNumEnteroRecAux(List<String> lineas, List<String> prov, Integer i,
			Integer cont){
		List<String> res = new ArrayList<String>();
		if(i >= lineas.size()) {
			res.addAll(prov);
		} else {
			if(cont>= lineas.get(i).length()) {
				i++;
				res = listNumEnteroRecAux(lineas,prov,i,0);
			} else {
				prov.add(lineas.get(i).substring(cont, cont+1).trim());
				cont=cont+2;
				res = listNumEnteroRecAux(lineas,prov,i,cont);
			}
		}
		return res;
	}
	
}
