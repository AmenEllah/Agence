import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgenceSharedModule } from 'app/shared';
import {
    LieuComponent,
    LieuDetailComponent,
    LieuUpdateComponent,
    LieuDeletePopupComponent,
    LieuDeleteDialogComponent,
    lieuRoute,
    lieuPopupRoute
} from './';

const ENTITY_STATES = [...lieuRoute, ...lieuPopupRoute];

@NgModule({
    imports: [AgenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LieuComponent, LieuDetailComponent, LieuUpdateComponent, LieuDeleteDialogComponent, LieuDeletePopupComponent],
    entryComponents: [LieuComponent, LieuUpdateComponent, LieuDeleteDialogComponent, LieuDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceLieuModule {}
