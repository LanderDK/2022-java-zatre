
//package Language;

//import java.util.Locale;
//import java.util.Scanner;
//import java.util.ResourceBundle;

//public class PropertyResourceBundle 
//{
//   public static void main(String[] args) 
//   {
//    	Locale locale_nl_NL = new Locale("nl", "NL");
//		Locale locale_en_EN = new Locale("en", "EN");
//   	try (Scanner input = new Scanner(System.in)) 
//    	{
//			int number1;
			
			
			
//			System.out.print("Choose a language. Press (1) for English or (2) for Dutch: "); 
//			number1=input.nextInt();
//			if (number1 == 1)
//			{
//				System.out.println("You have selected the language English");
//				ResourceBundle resourceBundle1 = ResourceBundle.getBundle("res.bundle", locale_en_EN);
//				System.out.println(resourceBundle1.getString("welcome"));
//			    
//			}
//			else if (number1 == 2) 
//			{
//				System.out.println("Je hebt de taal Nederlands gekozen");
//				ResourceBundle resourceBundle2 = ResourceBundle.getBundle("res.bundle", locale_nl_NL);
//				System.out.println(resourceBundle2.getString("welcome"));
//		
//			}
//			else 
//			{
//				System.out.println("Error, please restart the program");
//			}
//			ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", number1 == 1 ? locale_en_EN : locale_nl_NL);
//			System.out.println(resourceBundle.getString("hello"));
//		}
//   }
//}