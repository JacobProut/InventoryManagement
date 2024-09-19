Inventory Management Project
Project was created for WGU Software 1 Course. 
  - This project does not involve the usage of databases.

How the proect works: 
  - There is 2 tables
      - Parts Table
      - Products Table
  - Both tables have "Add, Modify, Delete" Buttons.

  - On the Parts table:
    - Clicking "Add"
        - On this page there is 2 radio buttons named: In-House & Outsourced.
            - When In-House is selected: The bottom field is called "Machine ID".
            - When Outsourced is selected: The bottom field is called "Company Name".
        - Also on this page is fields for ID, Name, Inv, Price/Cost, Max, Min, & Machine ID/Company Name.
          - ID is automatically generated.
      - Clicking "Save" adds the Part to the table.
      - Clicking "Cancel" returns to Inventory Management Page.

     - Clicking "Modify"
         - A Part needs to be selected in the table to modify it.
         - The Modify form is the same as the add form.
           - This form modifies an existing part.
         - Clicking "Cancel" returns to Inventory Management Page.

    - Clicking "Delete" will delete the SELECTED Part.


   - On the Product table:
    - Clicking "Add"
        - On this page there is fields for ID[Auto-Generated], Name, Inv, Price, Max, Min.
        -  There is also 2 tables on this page. One for Available Parts and one for Associated Parts.
            - When a part is added, it will be put into the associated Parts table.
        - ID is automatically generated.
        - Selecting a part from the associated parts table and selecting "Remove Associated Part" will remove it form the table and return it the Parts table.
      - Clicking "Save" adds the Product to the table.
          - All associated parts will be linked with the product.
      - Clicking "Cancel" returns to Inventory Management Page.

     - Clicking "Modify"
         - A Product needs to be selected in the table to modify it.
         - The Modify form is the same as the add form.
           - This form modifies an existing product.
         - Clicking "Cancel" returns to Inventory Management Page.

      - Clicking "Delete" will delete the SELECTED Product.

