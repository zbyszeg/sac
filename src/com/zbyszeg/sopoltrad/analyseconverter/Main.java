package com.zbyszeg.sopoltrad.analyseconverter;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.put("OptionPane.cancelButtonText", "Anuluj");
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		String filebody = "";
		String all = "";
		String location = args[0];
		String c = "\\";
		String name, filename, vb, newFile;
		String newName = "";

		int condition = 0;
		int condition2 = 0;

		Parser parser = new Parser();
		Progress window = new Progress();
		Saver saver = new Saver();
		ExcelEdit editor = new ExcelEdit();

		while (condition != 1) {
			window.setVisible(false);
			name = (String) JOptionPane.showInputDialog(null, "Wprowadź nazwę pliku XML z analizą:   ",
					"Sopoltrad Analyse Converter", JOptionPane.QUESTION_MESSAGE, null, null, "analyse.xml");

			if (name == null)
				System.exit(1);

			if (!name.contains(".xml") && !name.contains(".XML"))
				name += ".xml";

			filename = location + c + name;

			parser.setFileName(filename);

			int dot = name.indexOf('.');
			newName = name.substring(0, dot);

			window.setVisible(true);

			parser.parse();

			filebody = parser.getFileBody();
			all += "Words:\n" + parser.getAll();
			condition = parser.getCondition();
		}

		StringBuilder _filebody = new StringBuilder(filebody);
		saver.setFilebody((_filebody.insert(0, all + "\n\n")).toString());

		while (condition2 != 1) {
			saver.save();
			condition2 = saver.getCondition();
		}

		vb = "wscript C:\\SopoltradStudio\\macro.vbs " + "\"" + location + "\" \"" + newName + "\"";
		try {
			Runtime.getRuntime().exec(vb);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "System napotkał problem przy uruchamianiu skrytpu VBS.",
					"Błąd wykonania skryptu", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		newFile = location + c + newName + ".xlsx";
		editor.setNewFile(newFile);
		Thread.sleep(1000);
		editor.editor();
		window.setVisible(false);

		JOptionPane.showMessageDialog(null, "Zapisano plik '" + newName + ".xlsx'", "Zakończono pomyślnie",
				JOptionPane.INFORMATION_MESSAGE);

		System.exit(0);
	}
}
