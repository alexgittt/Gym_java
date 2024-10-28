Feature: Bag

  Scenario: Remove a product from the bag with 2 products
    Given there are products with variantID 39654522814667, 39654813106379 in the bag
    When I remove a product with variantID 39654813106379
    Then the product 39654813106379 is removed from the bag
    Then the product 39654522814667 is visible in the bag

  Scenario: Add quantity
    Given there is a product with variantID 39654522814667 in the bag
    When I add quantity 3
    Then product quantity is increased to "3"

  Scenario: Empty the bag
    Given there is a product with variantID 39654522814667 in the bag
    When I remove a product with variantID 39654522814667
    Then the bag is empty