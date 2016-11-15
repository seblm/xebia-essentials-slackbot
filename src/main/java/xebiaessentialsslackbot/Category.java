package xebiaessentialsslackbot;

class Category {

    private final String name;
    private final String colour;

    Category(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;
        return colour.equals(category.colour);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + colour.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + " " + colour;
    }

}
