import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AgenceSharedModule } from 'app/shared';
import {
    ConfortComponent,
    ConfortDetailComponent,
    ConfortUpdateComponent,
    ConfortDeletePopupComponent,
    ConfortDeleteDialogComponent,
    confortRoute,
    confortPopupRoute
} from './';

const ENTITY_STATES = [...confortRoute, ...confortPopupRoute];

@NgModule({
    imports: [AgenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConfortComponent,
        ConfortDetailComponent,
        ConfortUpdateComponent,
        ConfortDeleteDialogComponent,
        ConfortDeletePopupComponent
    ],
    entryComponents: [ConfortComponent, ConfortUpdateComponent, ConfortDeleteDialogComponent, ConfortDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceConfortModule {}
