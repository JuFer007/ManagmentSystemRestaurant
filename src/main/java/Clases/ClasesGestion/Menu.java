package Clases.ClasesGestion;

public class Menu {
    private int idMenu;
    private String nombreMenu;
    private String categoria;

    public Menu(String nombreMenu, String categoria) {
        this.nombreMenu = nombreMenu;
        this.categoria = categoria;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
