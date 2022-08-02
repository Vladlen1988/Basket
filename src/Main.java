import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    private static Product[] products = {
            new Product("Молоко", 50),
            new Product("Хлеб", 14),
            new Product("Гречневая крупа", 80),
    };

    private static int[] amountOfPurchasedProducts = new int[products.length];

    private final static int EACH_ITEM_FREE = 3;

    public static void main(String[] args) {
        init();

        scanner = new Scanner(System.in);

        showAllProducts();

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");

            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            String[] strNumbers = input.split(" ");

            if (strNumbers.length != 2) {
                System.out.println("Вы неверно передали данные!");
                System.out.println("Пример: '1 10'");
                continue;
            }

            try {
                int productIndex = Integer.parseInt(strNumbers[0]);

                if (productIndex < 0) {
                    errorOutOfProductsRange("Индекс товара не может иметь отрицательное значение!");
                    continue;
                }
                if (productIndex > products.length) {
                    errorOutOfProductsRange("Индекс товара не может превышать предложенный вам индекс!");
                    continue;
                }

                try {
                    int productsAmount = Integer.parseInt(strNumbers[1]);

                    if (productsAmount < 0) {
                        System.out.println("Количество товаров не может быть отрицательным!");
                        continue;
                    }

                    amountOfPurchasedProducts[productIndex - 1] += productsAmount;
                } catch (NumberFormatException e) {
                    System.out.println("При вводе количества товаров используйте только положительные числа," +
                            " не используйте слова!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста укажите индекс товара в пределах от 1 до " + products.length +
                        " вместо слова " + strNumbers[0]);
            }
        }

        showResults();
    }

    private static void errorOutOfProductsRange(String errorText) {
        System.out.println("Индекс товара не может превышать предложенный вам индекс!");
        System.out.println("Используйте индекс товара из диапопзона: [1-" + products.length + "]!s");
    }

    public static void init() {
        for (int i = 0; i < amountOfPurchasedProducts.length; i++) {
            amountOfPurchasedProducts[i] = 0;
        }
    }

    public static void showAllProducts() {
        System.out.println("Список возможных товаров для покупки");

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i].toString());
        }
    }

    public static void showResults() {
        System.out.println("Ваша корзина:");

        int sum = 0;

        int newAmount = 0;

        for (int i = 0; i < amountOfPurchasedProducts.length; i++) {
            if (amountOfPurchasedProducts[i] > 0) {
                if (amountOfPurchasedProducts[i] < EACH_ITEM_FREE) {
                    ShowInfo(products[i].title, amountOfPurchasedProducts[i], amountOfPurchasedProducts[i], products[i].price);

                    sum += amountOfPurchasedProducts[i] * products[i].price;
                } else {
                    newAmount = amountOfPurchasedProducts[i] - (amountOfPurchasedProducts[i] / EACH_ITEM_FREE);

                    ShowInfo(products[i].title, amountOfPurchasedProducts[i], newAmount, products[i].price);
                    sum += newAmount * products[i].price;
                }
            }
        }

        System.out.println("Итого " + sum + " руб");
    }

    private static void ShowInfo(String title, int amountOfProducts, int accountedGoods, int productPrice){
        String additionalMessage = "";

        if(accountedGoods < amountOfProducts){
            additionalMessage = " с учётом скидки 100% за каждый " + EACH_ITEM_FREE + " товар.";
        }

        System.out.println(title + " " +
                amountOfProducts + " шт " +
                productPrice + " руб/шт " +
                (accountedGoods * productPrice) +
                " руб в сумме" + additionalMessage);
    }
}
