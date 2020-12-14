package snow.app.eduhub.util;

public class OptionModel {

    public OptionModel(Boolean isSelected, String name,Boolean isCorrect,String rightoption,String chooseOption,String selectedOption) {
        this.isSelected = isSelected;
        this.isCorrect = isCorrect;
        this.rightoption = rightoption;
        this.chooseOption = chooseOption;
        this.selectedOption = selectedOption;
        this.name = name;

    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    Boolean isSelected=false;

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    Boolean isCorrect=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getRightoption() {
        return rightoption;
    }

    public void setRightoption(String rightoption) {
        this.rightoption = rightoption;
    }

    String rightoption;

    public String getChooseOption() {
        return chooseOption;
    }

    public void setChooseOption(String chooseOption) {
        this.chooseOption = chooseOption;
    }

    String chooseOption;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    String selectedOption;




}
