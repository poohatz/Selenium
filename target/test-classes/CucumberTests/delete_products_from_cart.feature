Feature: As a User I want to delete products from Cart



  Scenario: Deleting one product
    Given User is in Cart_page with One Product "sa32" from "Vintage&Nature"
    When User clicks Delete button near "sa32" Product
    Then Product "sa32" was deleted

  Scenario Outline: Deleting one product
    Given User is in Cart_page with One Product <symbol> from <category>
    When User clicks Delete button near <symbol> Product
    Then Product <symbol> was deleted
    Examples:
      | category          | symbol    | symbol     |
      | "Pastellove"      | "PL47"    | "PL47"     |
      | "Mystic Moment"   | "mm04"    | "mm04"     |



  Scenario: Deleting two products
    Given User is in Cart_page with Two Product "sa32" from "Vintage&Nature" and "PL47" from "Pastellove"
    When User clicks Delete button near "sa32" Product
    And User clicks Delete button near "PL47" Product
    Then Products "sa32" and "PL47" were deleted

  Scenario Outline: Deleting two products
    Given User is in Cart_page with Two Product <symbol> from <category> and <symbol2> from <category2>
    When User clicks Delete button near <symbol> Product
    And User clicks Delete button near <symbol2> Product
    Then Products <symbol> and <symbol2> were deleted
    Examples:
      | category          | symbol    | category2         |  symbol2    |
      | "Pastellove"      | "PL47"    | "Vintage&Nature"  | "sa32"      |
      | "Mystic Moment"   | "mm04"    | "Pastellove"      | "PL47"      |