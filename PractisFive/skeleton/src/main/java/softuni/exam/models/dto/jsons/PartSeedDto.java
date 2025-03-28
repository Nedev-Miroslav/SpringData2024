package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;

public class PartSeedDto {

    @Expose
    @NotNull
    @Size(min = 2, max = 19)
    private String partName;

    @Expose
    @DecimalMin("10.00")
    @DecimalMax("2000.00")
    private double price;

    @Expose
    @Positive
    private int quantity;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
