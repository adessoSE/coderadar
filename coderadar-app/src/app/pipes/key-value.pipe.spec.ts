import {KeyValuePipe} from './key-value.pipe';

describe('KeyValuePipe', () => {
    it('create an instance', () => {
        const pipe = new KeyValuePipe();
        expect(pipe).toBeTruthy();
    });
});
