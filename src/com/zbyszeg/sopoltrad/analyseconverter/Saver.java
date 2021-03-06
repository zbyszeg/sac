package com.zbyszeg.sopoltrad.analyseconverter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Saver {
	private int condition;
	private String fileBody;

	public void setFilebody(String fileBody) {
		this.fileBody = fileBody;
	}

	public void save() {
		try {
			PrintWriter save = new PrintWriter("Analyse");
			save.print(fileBody);
			save.close();

			condition = 1;

		} catch (FileNotFoundException e) {
			int response = JOptionPane.showConfirmDialog(null,
					"Prawdopodobnie plik jest otwarty w programie Microsoft Excel.\nSpróbuj zamknąć program w Menedżerze zadań i kliknij OK.",
					"Problem z dostępem do pliku tymczasowego 'Analyse'", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (response == JOptionPane.CANCEL_OPTION)
				System.exit(1);
		}
	}

	public int getCondition() {
		return condition;
	}
}
