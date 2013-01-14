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
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Intent intent = getIntent();
	mode = Integer.parseInt(intent.getStringExtra("mode"));
	max = Integer.parseInt(intent.getStringExtra("difficulty")) * 10;

	LinearLayout rootLayout = new LinearLayout(this);
	LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT);

	rootLayout.setOrientation(LinearLayout.VERTICAL);
	questionTextViews = new TextView[QuestionNumber];
	answerButtons = new Button[QuestionNumber];
	proposalButtons = new Button[QuestionNumber];
	for (int i = 0; i < QuestionNumber; ++i) {
	    LinearLayout lineLayout = new LinearLayout(this);
	    LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(
		    LinearLayout.LayoutParams.MATCH_PARENT,
		    LinearLayout.LayoutParams.WRAP_CONTENT);
	    LinearLayout.LayoutParams widgetLayoutParams = new LinearLayout.LayoutParams(
		    LinearLayout.LayoutParams.MATCH_PARENT,
		    LinearLayout.LayoutParams.WRAP_CONTENT, 1);

	    lineLayout.setOrientation(LinearLayout.HORIZONTAL);
	    questionTextViews[i] = new TextView(this);
	    questionTextViews[i].setGravity(Gravity.CENTER_HORIZONTAL);
	    questionTextViews[i].setText("????");
	    questionTextViews[i].setTextSize(30);

	    answerButtons[i] = new Button(this);
	    answerButtons[i].setText("????");
	    answerButtons[i].setTextSize(30);

	    proposalButtons[i] = new Button(this);
	    proposalButtons[i].setText("????");
	    proposalButtons[i].setTextSize(30);

	    lineLayout.addView(questionTextViews[i], widgetLayoutParams);
	    lineLayout.addView(answerButtons[i], widgetLayoutParams);
	    lineLayout.addView(proposalButtons[i], widgetLayoutParams);

	    rootLayout.addView(lineLayout, lineLayoutParams);
	}

	setContentView(rootLayout, rootLayoutParams);

	init();
    }

    private void init() {
	lines = new int[QuestionNumber];
	answers = new int[QuestionNumber][QuestionNumber];

	remainingProposalNumber = QuestionNumber;
	correctAnswerNumber = 0;

	// Button retour = (Button) findViewById(R.id.retourButton);
	// retour.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// finish();
	// }
	// });
	// Button next = (Button) findViewById(R.id.next);
	// next.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// shuffle();
	// }
	// });
	// Button recommencer = (Button) findViewById(R.id.recommencer);
	// recommencer.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// recreate();
	// }
	// });

	shuffle();

	for (int i = 0; i < QuestionNumber; i++) {
	    final int ifinal = i;
	    answerButtons[i].setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		    for (int j = 0; j < QuestionNumber; j++) {
			Button proposal = proposalButtons[j];

			if (mode == 1) {
			    proposal.setText(String.valueOf(answers[ifinal][j]));
			} else {
			    proposal.setText(numbers[answers[ifinal][j]]);
			}
			proposal.setVisibility(android.view.View.VISIBLE);
			proposal.setClickable(true);

			final int jfinal = j;
			proposal.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
				Button question = answerButtons[ifinal];

				if (mode == 1) {
				    question.setText(String
					    .valueOf(answers[ifinal][jfinal]));
				} else {
				    question.setText(numbers[answers[ifinal][jfinal]]);
				}
				hideProposals();
				check();
			    }
			});
		    }
		}
	    });
	}

    }

    private void check() {
	boolean finished = true;
	for (int i = 0; i < QuestionNumber; i++) {
	    if (answerButtons[i].getText().equals("______"))
		finished = false;
	}

	if (finished) {
	    for (int i = 0; i < QuestionNumber; i++) {
		TextView question = questionTextViews[i];
		boolean reponseCorrecte = true;

		answerButtons[i].setClickable(false);
		if (mode == 1) {
		    if (!numbers[Integer.parseInt(String.valueOf(answerButtons[i]
			    .getText()))].equals(question.getText())) {
			reponseCorrecte = false;
		    }
		} else {
		    if (!numbers[Integer.parseInt(String.valueOf(question
			    .getText()))].equals(answerButtons[i].getText())) {
			reponseCorrecte = false;
		    }
		}

		if (!reponseCorrecte) {
		    answerButtons[i].setTextColor(android.graphics.Color.RED);
		    if (mode == 1) {
			answerButtons[i].setText(String.valueOf(lines[i]));
		    } else {
			answerButtons[i].setText(numbers[lines[i]]);
		    }
		    answerButtons[i].setVisibility(android.view.View.VISIBLE);
		} else {
		    ++correctAnswerNumber;
		    answerButtons[i].setVisibility(android.view.View.INVISIBLE);
		}
	    }
//	    remainingProposalNumber--;
//	    if (remainingProposalNumber > 0) {
//		Button next = (Button) findViewById(R.id.next);
//		next.setVisibility(android.view.View.VISIBLE);
//	    } else {
//		TextView scoreFinal = (TextView) findViewById(R.id.scoreFinal);
//		scoreFinal.setText("Résultat : " + bonnesReponses + "/25");
//		scoreFinal.setVisibility(android.view.View.VISIBLE);
//
//		Button recommencer = (Button) findViewById(R.id.recommencer);
//		recommencer.setVisibility(android.view.View.VISIBLE);
//		if (bonnesReponses == 25) {
//		    ImageView gg = (ImageView) findViewById(R.id.gg);
//		    gg.setVisibility(android.view.View.VISIBLE);
//		}
//	    }
	}

    }

    public void setTextViewText(int TVid, String text) {
	TextView tv = (TextView) findViewById(TVid);
	tv.setText(text);
    }

    public void setButtonText(int Bid, String text) {
	Button bt = (Button) findViewById(Bid);
	bt.setText(text);
    }

    public int[] getNumbersAtRandom(int fixed) {
	int lignes[] = new int[QuestionNumber];
	lignes[0] = fixed;

	for (int i = 1; i < QuestionNumber; i++) {
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

	int shuffleWith = (int) (Math.random() * QuestionNumber);

	lignes[0] = lignes[shuffleWith];
	lignes[shuffleWith] = fixed;

	return lignes;
    }

    public int[] getNumbersAtRandom() {
	return getNumbersAtRandom((int) (Math.random() * max));
    }

    private void hideProposals() {
	for (int i = 0; i < QuestionNumber; ++i) {
	    proposalButtons[i].setVisibility(android.view.View.INVISIBLE);
	}
    }

    public void shuffle() {
	lines = getNumbersAtRandom();

	for (int i = 0; i < QuestionNumber; i++) {
	    answers[i] = getNumbersAtRandom(lines[i]);

	    String texteQuestion = String.valueOf(lines[i]);
	    if (mode == 1) {
		texteQuestion = String.valueOf(numbers[lines[i]]);
	    }
	    questionTextViews[i].setText(texteQuestion);

	    Button c2 = answerButtons[i];

	    c2.setClickable(true);
	    c2.setText("______");
	    c2.setTextColor(android.graphics.Color.BLACK);
	}
	hideProposals();

//	Button next = (Button) findViewById(R.id.next);
//	next.setVisibility(android.view.View.INVISIBLE);

	// TextView scoreFinal = (TextView) findViewById(R.id.scoreFinal);
	// scoreFinal.setVisibility(android.view.View.INVISIBLE);
	//
	// Button recommencer = (Button) findViewById(R.id.recommencer);
	// recommencer.setVisibility(android.view.View.INVISIBLE);

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

    private static int QuestionNumber = 5;

    private TextView[] questionTextViews;
    private Button[] answerButtons;
    private Button[] proposalButtons;

    private int lines[];
    private int answers[][];

    private int remainingProposalNumber;
    private int correctAnswerNumber;
    private int max;
    private int mode;
}
