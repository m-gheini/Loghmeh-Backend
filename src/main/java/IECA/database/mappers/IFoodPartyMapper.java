package IECA.database.mappers;

import IECA.logic.Location;
import IECA.logic.SaleFood;

import java.sql.SQLException;

interface IFoodPartyMapper extends IMapper<SaleFood, String> {
    void emptyTable() throws SQLException;
}
