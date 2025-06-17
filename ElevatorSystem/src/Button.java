public abstract class Button {

    private boolean isPressed;

    private final String label;

    public Button(String label) {
        this.isPressed = false;
        this.label = label;
    }

    public void press() {
        if (!isPressed) {
            isPressed = true;
            System.out.println(label + " pressed.");
            onPress(); // Abstract method for specific button logic
        }
    };

    public void unpress() {
        if (isPressed) {
            isPressed = false;
            System.out.println(label + " unpressed.");
        }
    }

    public abstract void onPress();
}