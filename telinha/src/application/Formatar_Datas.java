package application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatar_Datas {
	public static String Formatar_Para_Usuario(String data) {
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date data_formatar = formatoEntrada.parse(data);
            return formatoSaida.format(data_formatar);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public static String Formatar_Para_Banco(String data) {
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data_formatar = formatoEntrada.parse(data);
            return formatoSaida.format(data_formatar);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
	}
}
