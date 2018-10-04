import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgenceSharedModule } from 'app/shared';
import {
    VoyageComponent,
    VoyageDetailComponent,
    VoyageUpdateComponent,
    VoyageDeletePopupComponent,
    VoyageDeleteDialogComponent,
    voyageRoute,
    voyagePopupRoute
} from './';

const ENTITY_STATES = [...voyageRoute, ...voyagePopupRoute];

@NgModule({
    imports: [AgenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VoyageComponent, VoyageDetailComponent, VoyageUpdateComponent, VoyageDeleteDialogComponent, VoyageDeletePopupComponent],
    entryComponents: [VoyageComponent, VoyageUpdateComponent, VoyageDeleteDialogComponent, VoyageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceVoyageModule {}
