import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgenceSharedModule } from 'app/shared';
import {
    HebergementComponent,
    HebergementDetailComponent,
    HebergementUpdateComponent,
    HebergementDeletePopupComponent,
    HebergementDeleteDialogComponent,
    hebergementRoute,
    hebergementPopupRoute
} from './';

const ENTITY_STATES = [...hebergementRoute, ...hebergementPopupRoute];

@NgModule({
    imports: [AgenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HebergementComponent,
        HebergementDetailComponent,
        HebergementUpdateComponent,
        HebergementDeleteDialogComponent,
        HebergementDeletePopupComponent
    ],
    entryComponents: [HebergementComponent, HebergementUpdateComponent, HebergementDeleteDialogComponent, HebergementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceHebergementModule {}
