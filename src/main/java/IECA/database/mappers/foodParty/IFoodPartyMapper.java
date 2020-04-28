package IECA.database.mappers.foodParty;

import IECA.database.mappers.IMapper;
import IECA.logic.Location;
import IECA.logic.SaleFood;

import java.sql.SQLException;

interface IFoodPartyMapper extends IMapper<SaleFood, String> {
    void emptyTable() throws SQLException;
}
