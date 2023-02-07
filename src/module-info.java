module HelloFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens emotionalsongs to javafx.graphics, javafx.fxml, javafx.base;
}
