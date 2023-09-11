module GoldNuggetHunter {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.desktop;
	requires javafx.base;
	
	opens ch.sean.goldnugget to javafx.fxml;
	exports ch.sean.goldnugget;
}