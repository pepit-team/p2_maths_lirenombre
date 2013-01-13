/**
 * @file org/pepit/p2/maths/lirenombre/ExerciseActivity.java
 * 
 * PepitModel: an educational software
 * This file is a part of the PepitModel environment
 * http://pepit.be
 *
 * Copyright (C) 2012-2013 Pepit Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.pepit.p2.maths.lirenombre;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerciseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_jeu_chiffres_vers_nombres);

	SharedPreferences settings = getSharedPreferences("MyPrefs",
		Context.MODE_PRIVATE);
	mode = settings.getInt("mode", 0);
	max = settings.getInt("difficulte", 3) * 10;

	propositionsRestantes = 5;
	bonnesReponses = 0;

	Button retour = (Button) findViewById(R.id.retourButton);
	retour.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		finish();
	    }
	});
	Button next = (Button) findViewById(R.id.next);
	next.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		shuffle();
	    }
	});
	Button recommencer = (Button) findViewById(R.id.recommencer);
	recommencer.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		recreate();
	    }
	});
	shuffle();

	for (int i = 0; i < 5; i++) {
	    Button question = (Button) findViewById(ids[i]);
	    final int ifinal = i;
	    question.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		    for (int j = 0; j < 5; j++) {
			Button proposition = (Button) findViewById(ids[j + 5]);

			proposition.setText(numbers[answers[ifinal][j]]);
			if (mode == 1)
			    proposition.setText(String
				    .valueOf(answers[ifinal][j]));

			proposition.setVisibility(android.view.View.VISIBLE);
			proposition.setClickable(true);

			final int jfinal = j;
			proposition
				.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View v) {
					Button question = (Button) findViewById(ids[ifinal]);

					question.setText(numbers[answers[ifinal][jfinal]]);
					if (mode == 1)
					    question.setText(String
						    .valueOf(answers[ifinal][jfinal]));

					for (int r = 5; r < 10; r++) {
					    Button reponseBis = (Button) findViewById(ids[r]);
					    reponseBis
						    .setVisibility(android.view.View.INVISIBLE);
					}
					testFinish();
				    }
				});
		    }
		}
	    });
	}
    }

    private void testFinish() {
	boolean finished = true;
	for (int i = 0; i < 5; i++) {
	    Button reponse = (Button) findViewById(ids[i]);
	    if (reponse.getText().equals("______"))
		finished = false;
	}

	if (finished) {
	    for (int i = 0; i < 5; i++) {
		Button reponsePossible = (Button) findViewById(ids[i + 5]);
		reponsePossible.setClickable(false);

		TextView question = (TextView) findViewById(ids[i + 10]);
		Button reponse = (Button) findViewById(ids[i]);
		boolean reponseCorrecte = true;

		if (mode == 1) {
		    if (!numbers[Integer.parseInt(String.valueOf(reponse
			    .getText()))].equals(question.getText())) {
			reponseCorrecte = false;
		    }
		} else {
		    if (!numbers[Integer.parseInt(String.valueOf(question
			    .getText()))].equals(reponse.getText())) {
			reponseCorrecte = false;
		    }
		}

		if (!reponseCorrecte) {
		    reponse.setText("Faux ! " + reponse.getText());
		    reponse.setTextColor(android.graphics.Color.RED);

		    if (mode == 1) {
			reponsePossible.setText(String.valueOf(lines[i]));
		    } else {
			reponsePossible.setText(numbers[lines[i]]);
		    }

		    reponsePossible.setVisibility(android.view.View.VISIBLE);
		} else {
		    bonnesReponses++;
		    reponsePossible.setVisibility(android.view.View.INVISIBLE);
		}

		reponse.setClickable(false);

	    }
	    propositionsRestantes--;
	    if (propositionsRestantes > 0) {
		Button next = (Button) findViewById(R.id.next);
		next.setVisibility(android.view.View.VISIBLE);
	    } else {
		TextView scoreFinal = (TextView) findViewById(R.id.scoreFinal);
		scoreFinal.setText("Résultat : " + bonnesReponses + "/25");
		scoreFinal.setVisibility(android.view.View.VISIBLE);

		Button recommencer = (Button) findViewById(R.id.recommencer);
		recommencer.setVisibility(android.view.View.VISIBLE);
		if (bonnesReponses == 25) {
		    ImageView gg = (ImageView) findViewById(R.id.gg);
		    gg.setVisibility(android.view.View.VISIBLE);
		}
	    }
	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.activity_jeu_chiffres_vers_nombres,
		menu);
	return true;
    }

    public void setTextViewText(int TVid, String text) {
	TextView tv = (TextView) findViewById(TVid);
	tv.setText(text);
    }

    public void setButtonText(int Bid, String text) {
	Button bt = (Button) findViewById(Bid);
	bt.setText(text);
    }

    public int[] getFiveNumberAtRandom(int fixed) {
	int lignes[] = new int[5];
	lignes[0] = fixed;

	for (int i = 1; i < 5; i++) {
	    boolean flagOK = true;
	    do {
		flagOK = true;
		lignes[i] = (int) (Math.random() * max);
		for (int j = 0; j < i; j++) {
		    if (lignes[j] == lignes[i])
			flagOK = false;
		}
	    } while (flagOK == false);
	}

	int shuffleWith = (int) (Math.random() * 5);

	lignes[0] = lignes[shuffleWith];
	lignes[shuffleWith] = fixed;

	return lignes;
    }

    public int[] getFiveNumberAtRandom() {
	return getFiveNumberAtRandom((int) (Math.random() * max));
    }

    public void shuffle() {
	lines = getFiveNumberAtRandom();

	for (int i = 0; i < 5; i++) {
	    answers[i] = getFiveNumberAtRandom(lines[i]);

	    String texteQuestion = String.valueOf(lines[i]);
	    if (mode == 1)
		texteQuestion = String.valueOf(numbers[lines[i]]);

	    setTextViewText(ids[i + 10], texteQuestion);

	    Button c2 = (Button) findViewById(ids[i]);

	    c2.setClickable(true);
	    c2.setText("______");
	    c2.setTextColor(android.graphics.Color.BLACK);

	    Button proposition = (Button) findViewById(ids[i + 5]);
	    proposition.setVisibility(android.view.View.INVISIBLE);

	}

	Button next = (Button) findViewById(R.id.next);
	next.setVisibility(android.view.View.INVISIBLE);

	TextView scoreFinal = (TextView) findViewById(R.id.scoreFinal);
	scoreFinal.setVisibility(android.view.View.INVISIBLE);

	Button recommencer = (Button) findViewById(R.id.recommencer);
	recommencer.setVisibility(android.view.View.INVISIBLE);

    }

    private static String[] numbers = { "Zero", "Un", "Deux", "Trois",
	    "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Onze",
	    "Douze", "Treize", "Quatorze", "Quinze", "Seize", "Dix-sept",
	    "Dix-huit", "Dix-neuf", "Vingt", "Vingt et un", "Vingt-deux",
	    "Vingt-trois", "Vingt-quatre", "Vingt-cinq", "Vingt-six",
	    "Vingt-sept", "Vingt-huit", "Vingt-neuf", "Trente", "Trente et un",
	    "Trente-deux", "Trente-trois", "Trente-quatre", "Trente-cinq",
	    "Trente-six", "Trente-sept", "Trente-huit", "Trente-neuf",
	    "Quarante", "Quarante et un", "Quarante-deux", "Quarante-trois",
	    "Quarante-quatre", "Quarante-cinq", "Quarante-six",
	    "Quarante-sept", "Quarante-huit", "Quarante-neuf", "Cinquante",
	    "Cinquante et un", "Cinquante-deux", "Cinquante-trois",
	    "Cinquante-quatre", "Cinquante-cinq", "Cinquante-six",
	    "Cinquante-sept", "Cinquante-huit", "Cinquante-neuf", "Soixante",
	    "Soixante et un", "Soixante-deux", "Soixante-trois",
	    "Soixante-quatre", "Soixante-cinq", "Soixante-six",
	    "Soixante-sept", "Soixante-huit", "Soixante-neuf", "Soixante-dix",
	    "Soixante et onze", "Soixante-douze", "Soixante-treize",
	    "Soixante-quatorze", "Soixante-quinze", "Soixante-seize",
	    "Soixante-dix-sept", "Soixante-dix-huit", "Soixante-dix-neuf",
	    "Quatre-vingt", "Quatre-vingt-un", "Quatre-vingt-deux",
	    "Quatre-vingt-trois", "Quatre-vingt-quatre", "Quatre-vingt-cinq",
	    "Quatre-vingt-six", "Quatre-vingt-sept", "Quatre-vingt-huit",
	    "Quatre-vingt-neuf", "Quatre-vingt-dix", "Quatre-vingt-onze",
	    "Quatre-vingt-douze", "Quatre-vingt-treize",
	    "Quatre-vingt-quatorze", "Quatre-vingt-quinze",
	    "Quatre-vingt-seize", "Quatre-vingt-dix-sept",
	    "Quatre-vingt-dix-huit", "Quatre-vingt-dix-neuf", "Cent" };

    private static int lines[] = new int[5];
    private static int answers[][] = new int[5][5];
    private static int[] ids = { R.id.q1, R.id.q2, R.id.q3, R.id.q4, R.id.q5,
	    R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5, R.id.text1,
	    R.id.text2, R.id.text3, R.id.text4, R.id.text5 };

    private int propositionsRestantes;
    private int bonnesReponses;
    private int max;
    private int mode;

}
