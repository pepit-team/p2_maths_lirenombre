/**
 * @file org/pepit/p2/maths/lirenombre/Exercise.java
 * 
 * PepitMobil: an educational software
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

import java.net.MalformedURLException;
import java.net.URL;

import org.pepit.plugin.Info;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Exercise implements org.pepit.plugin.Interface {

    @Override
    public Info getInfo() {
	org.pepit.plugin.Info info = new org.pepit.plugin.Info();

	info.level = org.pepit.plugin.Level.P2;
	info.subject = org.pepit.plugin.Subject.MATHEMATICS;
	info.theme = "Lire un nombre";
	info.version = 1;
	String pepitPage = "http://www.pepit.be/exercices/primaire2/mathematiques/lirenombre/page.html";
	try {
	    info.pepitPage = new URL(pepitPage);
	} catch (MalformedURLException e) {
	    Log.e("Pepit", "Bad URL: " + pepitPage);
	}
	return info;
    }

    @Override
    public LinearLayout getExercisePresentationLayout(Context ctx) {
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT);
	params.gravity = Gravity.CENTER_HORIZONTAL;

	LinearLayout lil = new LinearLayout(ctx);
	lil.setLayoutParams(params);
	lil.setOrientation(LinearLayout.VERTICAL);
	lil.setBackgroundColor(0xFF99CC66);

	TextView tv1 = new TextView(ctx);
	tv1.setGravity(Gravity.CENTER_HORIZONTAL);
	tv1.setTextColor(Color.BLACK);
	tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
	tv1.setText("LIRE UN NOMBRE");
	lil.addView(tv1);

	TextView tv2 = new TextView(ctx);
	tv2.setGravity(Gravity.CENTER_HORIZONTAL);
	tv2.setTextColor(Color.BLACK);
	tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
	tv2.setText("Lire un nombre en lettres et en chiffre");
	lil.addView(tv2);

	return lil;
    }

    @Override
    public String[] getExerciseList() {
	String[] l = { "De 1 à 10", "De 1 à 20", "De 1 à 30", "De 1 à 40",
		"De 1 à 50", "De 1 à 60", "De 1 à 70", "De 1 à 80",
		"De 1 à 90", "De 1 à 100" };

	return l;
    }

    @Override
    public LinearLayout getExplanationPresentationLayout(Context ctx,
	    int selectedExercise) {
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT);
	params.gravity = Gravity.CENTER_HORIZONTAL;

	LinearLayout lil = new LinearLayout(ctx);
	lil.setLayoutParams(params);
	lil.setOrientation(LinearLayout.VERTICAL);
	lil.setBackgroundColor(0xFF99CC66);

	TextView tv1 = new TextView(ctx);
	tv1.setGravity(Gravity.CENTER_HORIZONTAL);
	tv1.setTextColor(Color.BLACK);
	tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
	tv1.setText("Comment jouer ?");
	lil.addView(tv1);

	TextView tv2 = new TextView(ctx);
	tv2.setGravity(Gravity.CENTER_HORIZONTAL);
	tv2.setTextColor(Color.BLACK);
	tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
	String[] t = this.getExerciseList();
	tv2.setText("\n" + t[selectedExercise] + "\n");
	lil.addView(tv2);

	return lil;
    }

    @Override
    public String[] getModuleList(int exercise) {
	String[] l = { "Module 1", "Module 2", "Module 3", "Module 4",
		"Module 5", "Module 6" };

	return l;
    }

    @Override
    public LinearLayout getQuestionLayout(Context ctx, int selectedExercise,
	    int selectedModule, int numQuestion) {
	view = new ExerciseView(ctx, (selectedModule < 5) ? 1 : 0,
		10 * (selectedExercise + 1));
	return view.getLayout();
    }

    @Override
    public int getQuestionCount(int selectedExercise, int selectedModule) {
	return 5;
    }

    @Override
    public void startQuestionSequence() {
    }

    @Override
    public void finishQuestionSequence() {
    }

    @Override
    public String getNextQuestionButtonText() {
	return "Valider";
    }

    @Override
    public boolean currentAnswerIsRight() {
	return view.check();
    }

    @Override
    public void showAnswerIsRight() {
    }

    @Override
    public void showAnswerIsWrong() {
    }

    @Override
    public int getPointsRightAnswer(int selectedExercise) {
	return 0;
    }

    private ExerciseView view;
}
