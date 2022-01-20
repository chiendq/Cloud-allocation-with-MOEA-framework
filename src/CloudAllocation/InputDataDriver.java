package CloudAllocation;

/**
 * @author
 *      Dao Quyet Chien 1901040037
 *      Do Ngoc Thanh Tung 1901040193
 * @overview
 *
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputDataDriver {
    private static final String PATH = "src/CloudAllocation/newData.xlsx";
    private static final int START_ROW = 20;

    public static void main(String[] args)  {
        List<NormalPlayer> normalPlayerList ;
        SpecialPlayer specialPlayer = new SpecialPlayer();
        int row = START_ROW;

        if(getValueOfCoordinate(row,0) != 0){
            int numberOfProperties =  getValueOfCoordinate(row,1); // row = 0;
            row = 21;
            specialPlayer.setNumberOfProperties(numberOfProperties);
            for (int i = 0; i < specialPlayer.getNumberOfProperties(); i++) {
                int  property = getValueOfCoordinate(row,i); //row = 1
                int weight  = getValueOfCoordinate(row+1,i); //row = 2
                specialPlayer.addProperty(property);
                specialPlayer.addWeight(weight);
            }
        }
        row = 23;
        //specialPlayer.displayInf();

        // get number of normal player & number of properties
        int numberOfNormalPlayer = getValueOfCoordinate(row,0);
        int numberOfProperties = getValueOfCoordinate(row, 1);
        int[] normalPlayerWeights = new int[numberOfProperties];
        //get weight of each strategy
        row = 24;
        for (int i = 0; i < numberOfProperties-1 ; i++) {
            normalPlayerWeights[i] = getValueOfCoordinate(row, i);
        }
        // normal player inf
        row = 25;
        normalPlayerList = new ArrayList<>(numberOfNormalPlayer);
        for (int i = 0; i <numberOfNormalPlayer; i++) {
            normalPlayerList.add(setNormalPlayer(row, numberOfProperties));
            row++;
        }
        displayNormalPlayerList(normalPlayerList);
    }

    /**
     *
     * @param normalPlayerList
     * @param row
     * @return List<Conflict>
     */
    private static List<Conflict> getConflictSet(List<NormalPlayer> normalPlayerList, int row){
        List<Conflict> conflictSet = new ArrayList<>();
        for(NormalPlayer normalPlayer : normalPlayerList){
            if(getStringOfCoordinate(row, 0).equals("x") == false){
                int col = 0;
                while(true){
                    if(getStringOfCoordinate(row, col) != null) {
                        String s = getStringOfCoordinate(row, col);
                        if(!s.equals("")) {
                            Conflict conflict = new Conflict(s);
                            conflictSet.add(conflict);
                        }
                        col++;
                    }
                    if(getStringOfCoordinate(row, col) == null) break;
                }
            }
            row+=1;
        }
        return conflictSet;
    }

    private static NormalPlayer setNormalPlayer(int row, int numberOfProperties){
        int strategiesOfPlayer = getValueOfCoordinate(row, 0);
        List<Strategy> strategies = new ArrayList<>(strategiesOfPlayer);
        int column = 1;
        for (int i = 0; i < strategiesOfPlayer; i++) {
            Strategy strategy = new Strategy();
            for (int j = 0; j < numberOfProperties; j++) {
                strategy.addProperty(getValueOfCoordinate(row, column));
                column++;
            }
            strategies.add(strategy);
        }
        return new NormalPlayer(strategies);
    }

    private static Integer getValueOfCoordinate(int rowIndex, int columnIndex)   {
        try {
            FileInputStream file = new FileInputStream(PATH);
            try (// create workbook for file
			XSSFWorkbook workbook = new XSSFWorkbook(file)) {
				// get the first sheet
				XSSFSheet sheet = workbook.getSheetAt(0);
				Row row = sheet.getRow(rowIndex);
				if(row.getCell(columnIndex) == null){
				    return null;
				}
				Cell cell = row.getCell(columnIndex);
				file.close();
				return (int) cell.getNumericCellValue();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getStringOfCoordinate(int rowIndex, int columnIndex) {
        try {
            FileInputStream file = new FileInputStream(PATH);
            // create workbook for file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // get the first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex);
            if (row.getCell(columnIndex) == null) {
                return null;
            }
            Cell cell = row.getCell(columnIndex);
            file.close();
            return cell.getStringCellValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void displayNormalPlayerList(List<NormalPlayer> normalPlayerList){
        for(NormalPlayer normalPlayer : normalPlayerList){
            System.out.println("Normal player : " + (normalPlayerList.indexOf(normalPlayer)+1));
            normalPlayer.display();
            System.out.println("---------------");
        }
    }
}
