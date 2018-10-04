import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgenceSharedModule } from 'app/shared';
import {
    EnvironnementComponent,
    EnvironnementDetailComponent,
    EnvironnementUpdateComponent,
    EnvironnementDeletePopupComponent,
    EnvironnementDeleteDialogComponent,
    environnementRoute,
    environnementPopupRoute
} from './';

const ENTITY_STATES = [...environnementRoute, ...environnementPopupRoute];

@NgModule({
    imports: [AgenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnvironnementComponent,
        EnvironnementDetailComponent,
        EnvironnementUpdateComponent,
        EnvironnementDeleteDialogComponent,
        EnvironnementDeletePopupComponent
    ],
    entryComponents: [
        EnvironnementComponent,
        EnvironnementUpdateComponent,
        EnvironnementDeleteDialogComponent,
        EnvironnementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceEnvironnementModule {}
