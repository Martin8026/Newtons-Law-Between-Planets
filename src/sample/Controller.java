package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.math.BigDecimal;

/**
 * Created by Martin on 16.3.2017
 * Repository branch: Git/SphereRep
 * Branch initiated: 16.3.2017, {master} branch
 */

public class Controller
{
    public Controller() {
        series = new XYChart.Series();
    }

    //isInt
    private boolean isInt(TextField input)
    {
        try{
            int number = Integer.parseInt(input.getText());
            System.out.println("IsInt " + number);
            return true;
        }
        catch(NumberFormatException e){
            System.out.println("Error: NaN");
            return false;
        }
    }

    //alertBox
    private static void alertBox(String title, String message, String message2)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        Label label1 = new Label(message);
        Label label2 = new Label(message2);
        VBox layout1 = new VBox(5);
        layout1.getChildren().addAll(label1,label2);
        layout1.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout1, 500,100);

        window.setTitle(title);
        window.setScene(scene1);
        window.show();
    }

    @FXML
    private TextField inputField1;
    @FXML
    private TextField inputField2;

    @FXML
    private TextField cubeInput;

    @FXML
    private LineChart<Number,Number> lineChart;

    private XYChart.Series series;

    public void initChart() {
        series.getData().clear();
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

    public void doIt()
    {
        if(isInt(inputField1) && isInt(inputField2) && isInt(cubeInput))
        {
            //r
            int r1 = Integer.parseInt(inputField1.getText());
            int r2 = Integer.parseInt(inputField2.getText());
            int border = Integer.parseInt(cubeInput.getText());

            //fragments
            BigDecimal total = SystemMethods.equation(r1, r2, border);
            System.out.println("Output for cubes: " + total + " N");
            String outputFragment = "Output for cubes: " + total + " N";

            //update chart
            double num = total.doubleValue() / Math.pow(10,20);

            XYChart.Data data = new XYChart.Data(border, num);
            series.getData().add(data);
            //full spheres
            double planetaryOutput = (6.67 * Math.pow(10, -11)) * ((5972 * 7347 * Math.pow(10, 40)) / Math.pow(1000 * (376292 + r1 + r2), 2));
            System.out.println("Output for full spheres: " + planetaryOutput + " N");
            String outputSpheres = "Output for full spheres: " + planetaryOutput + " N";

            alertBox("Output", outputFragment, outputSpheres);
        }
        else
            alertBox("Error", "Error: NaN", "");
    }
}
