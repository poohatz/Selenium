Feature: As a User I want to change quantity of products in cart



  Scenario: Change of one product quantity
    Given User is in Cart Page with One Product "sa32" from "Vintage&Nature"
    When User changes quantity to 33
    Then Quantity of 33 is displaying

  Scenario Outline: Change of different products quantity
    Given User is in Cart Page with One Product <symbol> from <category>
    When User changes quantity to <quantity>
    Then Quantity of <quantity> is displaying
    Examples:
      | symbol    | category         | quantity  | quantity |
      | "PL47"    | "Pastellove"     | 123       | 123      |
      | "mm04"    | "Mystic Moment"  | 12        |12        |



  Scenario: Change of two products quantity
    Given User is in Cart Page with One Product "sa32" from "Vintage&Nature"
    When User changes quantity to 123 and checks PayU option, Standard option and click Calculate and Save Button
    And User goes into Category Page "Mystic Moment"
    And User clicks Add to Cart near Product "mm04"
    And User changes quantity to 21 and click Calculate and Save Button
    Then Quantity of "sa32" wynosi 123 and quantity of "mm04" wynosi 21 and total amount wynosi 740.70

  Scenario Outline: Change of two products quantity
    Given User is in Cart Page with One Product <symbol> from <category>
    When User changes quantity to <quantity> and checks PayU option, Standard option and click Calculate and Save Button
    And User goes into Category Page <category2>
    And User clicks Add to Cart near Product <symbol2>
    And User changes quantity to <quantity2> and click Calculate and Save Button
    Then Quantity of <symbol> wynosi <quantity> and quantity of <symbol2> wynosi <quantity2> and total amount wynosi <totalAmount>
    Examples:
      | symbol    | category         | quantity  | category2        |  symbol2 |quantity2| totalAmount |
      | "sa32"    | "Vintage&Nature" | 123       | "Mystic Moment"  |  "mm07"  |  21     |   740.70    |

